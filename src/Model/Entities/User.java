package Model.Entities;


public class User {
	private String userId;
	private String userPassword;
	private int userType;
	private String userEmail;
	private String userFirstName;
	private String userLastName;

	public User(String userId, String password, int type, String email, String firstName, String lastName) {
		this.userId = userId;
		this.userPassword = password;
		this.userType = type;
		this.userEmail = email;
		this.userFirstName = firstName;
		this.userLastName = lastName;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public int getUserType() {
		return userType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}
}
