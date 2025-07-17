package Model.Entities;

public class User {
	private String userId;
	private String password;
	private int type;
	private String email;

	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public User(String userId, String password, int type, String email) {
		this.userId = userId;
		this.password = password;
		this.type = type;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public int getType() {
		return type;
	}

	public String getEmail() {
		return email;
	}
}
