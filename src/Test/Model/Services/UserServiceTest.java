package Test.Model.Services;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.UserService;
import Model.Entities.User;

import java.util.List;

public class UserServiceTest {

	@Test
	public void testGetUserCountReturnsNonNegative() {
		UserService service = new UserService();
		int count = service.getUserCount();
		assertTrue(count >= 0);
	}

	@Test
	public void testGetAllUsersReturnsList() {
		UserService service = new UserService();
		List<User> users = service.getAllUsers();
		assertNotNull(users);
	}

	@Test
	public void testGetUserIdsReturnsCorrectIds() {
		UserService service = new UserService();
		List<User> users = service.getAllUsers();
		List<String> ids = service.getUserIds();
		assertEquals(users.size(), ids.size());
		for (User user : users) {
			assertTrue(ids.contains(user.getUserId()));
		}
	}

	@Test
	public void testValidateUserCredentialsWithCorrectCredentials() {
		UserService service = new UserService();
		// Use a known user from users.json
		short result = service.validateUserCredentials("31657526", "admin");
		assertEquals(0, result); // 0 = admin
		result = service.validateUserCredentials("13232510", "user");
		assertEquals(1, result); // 1 = user
	}

	@Test
	public void testValidateUserCredentialsWithIncorrectCredentials() {
		UserService service = new UserService();
		short result = service.validateUserCredentials("31657526", "wrongpassword");
		assertEquals(-1, result);
		result = service.validateUserCredentials("nonexistent", "admin");
		assertEquals(-1, result);
	}

	@Test
	public void testAddUserToDatabaseAndDuplicateUserId() {
		UserService service = new UserService();
		String userId = "99999999";
		User user = new User(userId, "testpassword", 1, "testuser@gmail.com", "Test", "User");
		boolean added = service.addUserToDatabase(user);
		assertTrue(added);

		// Try to add again with same userId (should still succeed, but validation should prevent in UI)
		User duplicate = new User(userId, "testpassword", 1, "testuser@gmail.com", "Test", "User");
		boolean addedAgain = service.addUserToDatabase(duplicate);
		assertTrue(addedAgain);
	}

	@Test
	public void testValidateRegistrationFieldsEmptyFields() {
		boolean valid = UserService.validateRegistrationFields(null, "", "", "", "");
		assertFalse(valid);
	}

	@Test
	public void testValidateRegistrationFieldsInvalidEmail() {
		boolean valid = UserService.validateRegistrationFields(null, "Test", "User", "notanemail", "12345678", "password123");
		assertFalse(valid);
	}

	@Test
	public void testValidateRegistrationFieldsInvalidUserId() {
		boolean valid = UserService.validateRegistrationFields(null, "Test", "User", "test@gmail.com", "abc123", "password123");
		assertFalse(valid);
	}

	@Test
	public void testValidateRegistrationFieldsShortPassword() {
		boolean valid = UserService.validateRegistrationFields(null, "Test", "User", "test@gmail.com", "12345678", "short");
		assertFalse(valid);
	}

	@Test
	public void testValidateRegistrationFieldsInvalidDomain() {
		boolean valid = UserService.validateRegistrationFields(null, "Test", "User", "test@notallowed.com", "12345678", "password123");
		assertFalse(valid);
	}

	@Test
	public void testValidateRegistrationFieldsValid() {
		boolean valid = UserService.validateRegistrationFields(null, "Test", "User", "test@gmail.com", "12345678", "password123");
		assertTrue(valid);
	}
}