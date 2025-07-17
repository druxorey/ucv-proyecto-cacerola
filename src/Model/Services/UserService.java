package Model.Services;

import Model.Entities.User;
import java.io.*;
import java.util.*;

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


	public boolean authenticate(String userId, String password) {
		List<User> users = loadUsers();
		for (User user : users) {
			if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
				return true;
			}	
		}	
		return false;
	}	
	

	private List<User> loadUsers() {
		List<User> users = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					users.add(new User(parts[0], parts[1]));
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading users: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}

		return users;
	}
}
