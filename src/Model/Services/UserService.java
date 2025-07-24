package Model.Services;

import Model.Entities.User;
import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UserService {
	private static final String USERS_FILE = "Model/Data/users.json";
	private static final String[] ALLOWED_DOMAINS = {"@gmail.com", "@ciens.ucv.ve", "@ucv.ve"};


	public int getUserCount() {
		return loadUsers().size();
	}


	public List<String> getUserIds() {
		List<String> userIds = new ArrayList<>();
		List<User> users = loadUsers();
		for (User user : users) {
			userIds.add(user.getUserId());
		}
		return userIds;
	}


	public short authenticate(String userId, String password) {
		List<User> users = loadUsers();
		for (User user : users) {
			if (user.getUserId().equals(userId) && user.getUserPassword().equals(password)) {
				if (user.getUserType() == 0) {
					return 2;
				}
				return 1;
			}	
		}	
		return 0;
	}	
	

	private List<User> loadUsers() {
		List<User> users = new ArrayList<>();
		try (FileReader reader = new FileReader(USERS_FILE)) {
			JSONParser parser = new JSONParser();
			JSONArray arr = (JSONArray) parser.parse(reader);
			for (Object o : arr) {
				JSONObject obj = (JSONObject) o;
				String userId = (String) obj.get("userId");
				String password = (String) obj.get("password");
				int type = ((Long) obj.get("userType")).intValue();
				String email = (String) obj.get("email");
				String firstName = (String) obj.get("firstName");
				String lastName = (String) obj.get("lastName");
				users.add(new User(userId, password, type, email, firstName, lastName));
			}
		} catch (Exception e) {
			System.err.println("Error loading users: " + e.getMessage());
		}
		return users;
	}


	@SuppressWarnings("unchecked")
	public boolean addUserToDatabase(User user) {
		List<User> users = loadUsers();
		users.add(user);
		JSONArray arr = new JSONArray();
		for (User u : users) {
			JSONObject obj = new JSONObject();
			obj.put("userId", u.getUserId());
			obj.put("password", u.getUserPassword());
			obj.put("userType", u.getUserType());
			obj.put("email", u.getUserEmail());
			obj.put("firstName", u.getUserFirstName());
			obj.put("lastName", u.getUserLastName());
			arr.add(obj);
		}
		try (FileWriter writer = new FileWriter(USERS_FILE)) {
			writer.write(arr.toJSONString());
			return true;
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, "Error al guardar usuario: " + e.getMessage());
			return false;
		}
	}


	public void generateUserCache(String userId) {
		String walletDirPath = "Model/Data/Wallets";
		String movementsDirPath = "Model/Data/Movements";

		String walletFilePath = walletDirPath + "/" + userId + "_wallet.json";
		String movementsFilePath = movementsDirPath + "/" + userId + "_movements.json";

		java.io.File walletFile = new java.io.File(walletFilePath);
		java.io.File movementsFile = new java.io.File(movementsFilePath);

		try {
			java.io.File walletDir = new java.io.File(walletDirPath);
			java.io.File movementsDir = new java.io.File(movementsDirPath);
			
			if (!movementsDir.exists()) movementsDir.mkdirs();
			if (!walletDir.exists()) walletDir.mkdirs();

			if (!walletFile.exists()) {
				String json = "{\n\"wallet\": 0\n}";
				java.nio.file.Files.write(walletFile.toPath(), json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
				System.out.println("[LoginController] Wallet file created for user: " + userId);
			} else {
				System.out.println("[LoginController] Wallet file already exists for user: " + userId);
			}

			if (!movementsFile.exists()) {
				String json = "[]"; // Empty array for movements
				java.nio.file.Files.write(movementsFile.toPath(), json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
				System.out.println("[LoginController] Movements file created for user: " + userId);
			} else {
				System.out.println("[LoginController] Movements file already exists for user: " + userId);
			}

		} catch (Exception ex) {
			System.err.println("[LoginController] Error creating wallet file for user: " + userId);
			ex.printStackTrace();
		}
	}


	public static boolean validateRegistrationFields(JFrame currentView, String firstName, String lastName, String email, String userId) {
		if (email.isEmpty() || userId.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			System.err.println("[RegisterController] Registration failed: One or more fields are empty. Email: '" + email + "', UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(currentView, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		List<String> existingUserIds = new UserService().getUserIds();
		if (existingUserIds.contains(userId)) {
			System.err.println("[RegisterController] Registration failed: UserID already exists. UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(currentView, "Ya existe un usuario con esa Cédula.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!userId.matches("\\d{7,8}")) {
			System.err.println("[RegisterController] Registration failed: Invalid ID format. UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(currentView, "Cédula de identidad inválida. Debe tener 7 u 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			System.err.println("[RegisterController] Registration failed: Invalid email format. Email: '" + email + "'");
			JOptionPane.showMessageDialog(currentView, "Correo electrónico inválido.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		boolean validDomain = false;
		for (String domain : ALLOWED_DOMAINS) {
			if (email.endsWith(domain)) {
				validDomain = true;
				break;
			}
		}

		if (!validDomain) {
			System.err.println("[RegisterController] Registration failed: Email domain not allowed. Email: '" + email + "'");
			JOptionPane.showMessageDialog(currentView, "Solo se permiten correos de los dominios: @gmail.com, @ciens.ucv.ve, @ucv.ve", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		System.out.println("[RegisterController] Registration fields validated successfully for UserID: '" + userId + "'. Email: '" + email + "'");
		return true;
	}
}
