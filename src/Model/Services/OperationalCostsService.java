
package Model.Services;

import java.io.*;
import java.nio.file.Paths;
import org.json.JSONObject;

public class OperationalCostsService {
	private static final String FILE_PATH = Paths.get("Model", "Data", "operational_costs.json").toString();

	public JSONObject loadOperationalCosts() {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return new JSONObject(sb.toString());
		} catch (Exception e) {
			System.err.println("Operational costs file not found or error reading: " + FILE_PATH);
			return new JSONObject();
		}
	}


	public boolean saveOperationalCosts(double totalFixedCosts, double variableCosts, int numberOfTrays, double wastePercentage) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
			JSONObject obj = new JSONObject();
			obj.put("totalFixedCosts", totalFixedCosts);
			obj.put("variableCosts", variableCosts);
			obj.put("numberOfTrays", numberOfTrays);
			obj.put("wastePercentage", wastePercentage);
			writer.write(obj.toString(2));
			return true;
		} catch (Exception e) {
			System.err.println("Error saving operational costs: " + e.getMessage());
			return false;
		}
	}
}
