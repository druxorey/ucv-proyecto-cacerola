package Model.Services;

import java.io.*;
import org.json.JSONObject;

public class OperationalCostsService {
	private static final String FILE_PATH = "/Model/Data/operationalCosts.json";

	public JSONObject loadOperationalCosts() {
		try {
			java.net.URL fileUrl = getClass().getResource(FILE_PATH);
			if (fileUrl != null) {
				try (InputStream in = fileUrl.openStream();
					 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					return new JSONObject(sb.toString());
				}
			} else {
				System.err.println("Operational costs file not found in resources: " + FILE_PATH);
				return new JSONObject();
			}
		} catch (Exception e) {
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
			return false;
		}
	}
}
