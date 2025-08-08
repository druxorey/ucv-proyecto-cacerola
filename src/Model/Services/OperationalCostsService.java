package Model.Services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class OperationalCostsService {
    private static final String FILE_PATH = "Model/Data/operational_costs.json";

	
	public JSONArray loadAllOperationalCosts() {
		File file = new File(FILE_PATH);
		if (!file.exists()) return new JSONArray();
		try (FileReader reader = new FileReader(file)) {
			JSONParser parser = new JSONParser();
			return (JSONArray) parser.parse(reader);
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray();
		}
	}


	public JSONObject loadOperationalCostsForWeek(LocalDate weekStart) {
		JSONArray arr = loadAllOperationalCosts();
		for (Object obj : arr) {
			JSONObject o = (JSONObject) obj;
			if (o.get("weekStart").equals(weekStart.toString())) return o;
		}
		return null;
	}


	public List<String> getAllWeeks() {
		JSONArray arr = loadAllOperationalCosts();
		List<String> weeks = new ArrayList<>();
		for (Object obj : arr) {
			JSONObject o = (JSONObject) obj;
			weeks.add(o.get("weekStart") + "->" + o.get("weekEnd"));
		}
		return weeks;
	}

	@SuppressWarnings("unchecked")
	public boolean saveOperationalCostsForWeek(LocalDate weekStart, LocalDate weekEnd, double totalFixed, double variable, int trays, double waste, double studentPct, double professorPct, double employeePct) {
		JSONArray arr = loadAllOperationalCosts();
		JSONObject found = null;
		for (Object obj : arr) {
			JSONObject o = (JSONObject) obj;
			if (o.get("weekStart").equals(weekStart.toString())) {
				found = o;
				break;
			}
		}
		if (found == null) {
			found = new JSONObject();
			arr.add(found);
		}
		found.put("weekStart", weekStart.toString());
		found.put("weekEnd", weekEnd.toString());
		found.put("totalFixedCosts", totalFixed);
		found.put("variableCosts", variable);
		found.put("numberOfTrays", trays);
		found.put("wastePercentage", waste);
		found.put("studentPct", studentPct);
		found.put("professorPct", professorPct);
		found.put("employeePct", employeePct);

		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			writer.write(arr.toJSONString());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public JSONObject calculateCCBAndAmounts(JSONObject costs) {
		JSONObject result = new JSONObject();
		double totalFixedCosts = getDouble(costs, "totalFixedCosts");
		double variableCosts = getDouble(costs, "variableCosts");
		int numberOfTrays = getInt(costs, "numberOfTrays");
		double wastePercentage = getDouble(costs, "wastePercentage");
		double studentPct = getDouble(costs, "studentPct");
		double professorPct = getDouble(costs, "professorPct");
		double employeePct = getDouble(costs, "employeePct");

		double ccb = 0.0;
		if (numberOfTrays > 0) {
			ccb = ((totalFixedCosts + variableCosts) / numberOfTrays) * (1 + wastePercentage / 100.0);
		}
		result.put("ccb", ccb);
		result.put("studentAmount", ccb * studentPct / 100.0);
		result.put("professorAmount", ccb * professorPct / 100.0);
		result.put("employeeAmount", ccb * employeePct / 100.0);
		return result;
	}

	private double getDouble(JSONObject obj, String key) {
		Object val = obj.get(key);
		if (val instanceof Number) return ((Number) val).doubleValue();
		return 0.0;
	}
	private int getInt(JSONObject obj, String key) {
		Object val = obj.get(key);
		if (val instanceof Number) return ((Number) val).intValue();
		return 0;
	}
}