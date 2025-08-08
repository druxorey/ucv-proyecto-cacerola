package Model.Services;

import org.json.simple.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;

public class MenuPriceService {
	private OperationalCostsService costsService = new OperationalCostsService();
	private MenuService menuService = new MenuService();

	public double calculateMenuPrice(String userId, LocalDate date, String shift) {
		JSONObject user = findUser(userId);
		if (user == null) return 0.0;

		LocalDate weekStart = date.with(DayOfWeek.SUNDAY);
		JSONObject costs = costsService.loadOperationalCostsForWeek(weekStart);
		JSONObject menu = menuService.loadMenu(date, shift);
		if (menu == null) return 0.0;

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

		int userType = ((Long) user.get("userType")).intValue();
		double pct = 100.0;
		if (userType == 1) pct = studentPct;
		else if (userType == 2) pct = professorPct;
		else if (userType == 3) pct = employeePct;

		double finalPrice = ccb * pct / 100.0;
		System.out.println("[MenuPriceService] Final price for user " + userId + " on " + date + " (" + shift + "): " + finalPrice);
		return finalPrice;
	}

	private JSONObject findUser(String userId) {
		try (FileReader reader = new FileReader("Model/Data/users.json")) {
			JSONParser parser = new JSONParser();
			List<?> users = (List<?>) parser.parse(reader);
			for (Object obj : users) {
				JSONObject user = (JSONObject) obj;
				if (userId.equals(user.get("userId"))) return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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