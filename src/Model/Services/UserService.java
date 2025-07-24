package Model.Services;

import Model.Entities.User;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UserService {
	private static final String USERS_FILE = "/Model/Data/users.enc";
	private static final String USERS_FILE_TEST = "Model/Data/users.enc";
	private static final String[] ALLOWED_DOMAINS = {"@gmail.com", "@ciens.ucv.ve", "@ucv.ve"};


	public int getUserCount() {
		int count = 0;
		try (InputStream in = getClass().getResourceAsStream(USERS_FILE)) {
			if (in != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				while (br.readLine() != null) {
					count++;
				}
			} else {
				System.err.println("User file not found in resources: " + USERS_FILE);
			}
		} catch (IOException e) {
			System.err.println("Error reading user file: " + e.getMessage());
		}
		return count;
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
		try (InputStream in = getClass().getResourceAsStream(USERS_FILE)) {
			if (in != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length == 6) {
						String userId = parts[0];
						String password = parts[1];
						int type = Integer.parseInt(parts[2]);
						String email = parts[3];
						String nombre = parts[4];
						String apellido = parts[5];
						users.add(new User(userId, password, type, email, nombre, apellido));
					}
				}
			} else {
				System.err.println("User file not found in resources: " + USERS_FILE);
			}
		} catch (IOException e) {
			System.err.println("Error loading users: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
		return users;
	}


	public boolean addUserToDatabase(User user) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE_TEST, true))) {
			String line = String.format("%s,%s,%d,%s,%s,%s", user.getUserId(), user.getUserPassword(), user.getUserType(), user.getUserEmail(), user.getUserFirstName(), user.getUserLastName());
			bw.write(line);
			bw.newLine();
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
