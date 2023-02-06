package models.user;

public class SystemUser {
	private String name;
	private String role;

	public SystemUser(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return name;
	}
}
