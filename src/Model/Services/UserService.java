package Model.Services;

import Model.Entities.User;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class UserService {
	private static final String USERS_FILE = "Model/Data/users.enc";

	public int getUserCount() {
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
			while (br.readLine() != null) {
				count++;
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


	public int authenticate(String userId, String password) {
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
		try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
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
		} catch (IOException e) {
			System.err.println("Error loading users: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
		return users;
	}

	public boolean addUserToDatabase(User user) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
			String line = String.format("%s,%s,%d,%s,%s,%s", user.getUserId(), user.getUserPassword(), user.getUserType(), user.getUserEmail(), user.getUserFirstName(), user.getUserLastName());
			bw.write(line);
			bw.newLine();
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al guardar usuario: " + e.getMessage());
			return false;
		}
	}
}
