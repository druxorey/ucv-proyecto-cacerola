package Model.Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class RegisterService {
	private static final String REQUEST_DIR = "Model/Data/Requests";

	
	@SuppressWarnings("unchecked")
	public static void saveRegistrationRequest(String firstName, String lastName, String email, String userId) throws IOException {
		File dir = new File(REQUEST_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println("[RegisterService] Registration request directory created: " + REQUEST_DIR);
		}

		int nextNumber = 1;
		File[] files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		if (files != null && files.length > 0) {
			for (File file : files) {
				String numStr = file.getName().substring(1, 7);
				int num = Integer.parseInt(numStr);
				if (num >= nextNumber) {
					nextNumber = num + 1;
				}
			}
		}

		String fileName = String.format("S%06d.json", nextNumber);

		JSONObject data = new JSONObject();
		data.put("firstName", firstName);
		data.put("lastName", lastName);
		data.put("email", email);
		data.put("userId", userId);

		try (FileWriter writer = new FileWriter(new File(dir, fileName))) {
			writer.write(data.toJSONString());
			System.out.println("[RegisterService] Registration request saved: " + fileName);
		} catch (IOException e) {
			System.err.println("[RegisterService] Error saving registration request: " + e.getMessage());
			throw e;
		}
	}
}
