package Model.Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

public class RegisterService {
	private static final String REQUEST_DIR = "Model/Data/Requests";
	private static final String COUNT_FILE = REQUEST_DIR + "/request_counts.txt";
	private static final int MAX_REQUESTS_PER_DAY = 20;

	private static String getNextAvailableDate() throws IOException {
		Map<String, Integer> counts = new HashMap<>();
		File countFile = new File(COUNT_FILE);
		if (countFile.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(countFile))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length == 2) {
						counts.put(parts[0], Integer.parseInt(parts[1]));
					}
				}
			}
		}

		LocalDate date = LocalDate.now();
		while (true) {
			DayOfWeek dow = date.getDayOfWeek();
			if (dow == DayOfWeek.TUESDAY || dow == DayOfWeek.WEDNESDAY || dow == DayOfWeek.THURSDAY) {
				String dateStr = date.toString();
				int count = counts.getOrDefault(dateStr, 0);
				if (count < MAX_REQUESTS_PER_DAY) {
					return dateStr;
				}
			}
			// If it's Tuesday, Wednesday, or Thursday, increment to the next available day
			if (date.getDayOfWeek() == DayOfWeek.THURSDAY) {
				date = date.plusDays(5); // Friday -> Tuesday
			} else {
				date = date.plusDays(1);
			}
		}
	}


	private static void incrementRequestCount(String dateStr) throws IOException {
		Map<String, Integer> counts = new HashMap<>();
		File countFile = new File(COUNT_FILE);
		if (countFile.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(countFile))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length == 2) {
						counts.put(parts[0], Integer.parseInt(parts[1]));
					}
				}
			}
		}
		counts.put(dateStr, counts.getOrDefault(dateStr, 0) + 1);

		try (FileWriter writer = new FileWriter(countFile, false)) {
			for (Map.Entry<String, Integer> entry : counts.entrySet()) {
				writer.write(entry.getKey() + "," + entry.getValue() + "\n");
			}
		}
	}


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

		String date = getNextAvailableDate();
		incrementRequestCount(date);

		JSONObject data = new JSONObject();
		data.put("firstName", firstName);
		data.put("lastName", lastName);
		data.put("email", email);
		data.put("userId", userId);
		data.put("date", date);

		try (FileWriter writer = new FileWriter(new File(dir, fileName))) {
			writer.write(data.toJSONString());
			System.out.println("[RegisterService] Registration request saved: " + fileName + " for date: " + date);
		} catch (IOException e) {
			System.err.println("[RegisterService] Error saving registration request: " + e.getMessage());
			throw e;
		}
	}


	public static void sendAppointmentEmail(String userId) throws Exception {
		File dir = new File(REQUEST_DIR);
		File[] files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		if (files == null) return;

		for (File file : files) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				JSONObject data = (JSONObject) org.json.simple.JSONValue.parse(sb.toString());
				if (data != null && userId.equals(data.get("userId"))) {
					String email = (String) data.get("email");
					String firstName = (String) data.get("firstName");
					String lastName = (String) data.get("lastName");
					String date = (String) data.get("date");
					String subject = "Confirmación de cita - Universidad Central de Venezuela";
					String body = "Hola " + firstName + " " + lastName + ",<br><br>"
						+ "Su cita ha sido registrada exitosamente.<br>"
						+ "Día de la cita: " + date + "<br>"
						+ "Lugar: <a href='https://maps.app.goo.gl/GLQibH9gsA9pEwTY6'>Oficina de la Secretaría, Universidad Central de Venezuela</a><br><br>"
						+ "Gracias por su solicitud.";
					EmailService.sendEmail(subject, body, email);
					return;
				}
			} catch (IOException e) {
				System.err.println("[RegisterService] Error reading file: " + file.getName() + ". Error: " + e.getMessage());
			} catch (Exception e) {
				System.err.println("[RegisterService] Error sending email for UserID: '" + userId + "'. Error: " + e.getMessage());
				throw e;
			}
		}
	}


	public static void deleteRegistrationRequestByUserId(String userId) {
		File dir = new File(REQUEST_DIR);
		File[] files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		if (files == null) return;

		for (File file : files) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				JSONObject data = (JSONObject) org.json.simple.JSONValue.parse(sb.toString());
				if (data != null && userId.equals(data.get("userId"))) {
					if (file.delete()) {
						System.out.println("[RegisterService] Registration request deleted for userId: " + userId);
					} else {
						System.err.println("[RegisterService] Failed to delete registration request for userId: " + userId);
					}
					return;
				}
			} catch (Exception e) {
				System.err.println("[RegisterService] Error deleting registration request for userId: " + userId + ". Error: " + e.getMessage());
			}
		}
	}
}