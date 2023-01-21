package auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import models.Admin;
import models.Student;
import models.SystemUser;
import models.Teacher;

public class Auth {
	private static Statement stmt;

	public Auth(util.DatabaseManager db) {
		Auth.stmt = db.getStatement();
	}

	public static SystemUser returnSystemUser(String email, String password) throws Exception {
		try {
			String sql = "SELECT count(*) FROM auth WHERE email='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			int count = 0;
			if (rs.next())
				count = rs.getInt(1);
			if (count == 0)
				throw new InvalidEmailException("No user with this email found!");
			else {
				sql = "SELECT name,role FROM auth WHERE email='" + email + "' AND password='" + password + "'";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					switch (rs.getString("role")) {
						case "Student":
							return new Student(rs.getString("name"));
						case "Teacher":
							return new Teacher(rs.getString("name"));
						case "Admin":
							return new Admin(rs.getString("name"));
						default:
							throw new Exception("An error occurred while retrieving role!");
					}
				} else
					throw new InvalidPasswordException("Password not valid!");
			}
		} catch (SQLException e) {
			throw new Exception("An error occurred while checking the email and password!");
		}
	}

	public static void addCredential(String name, String email, String password) throws Exception {
		try {
			String sql = "SELECT email FROM auth WHERE email='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				throw new InvalidEmailException("Email already in use!");
			} else {
				sql = "INSERT INTO auth (name, email, password, role) VALUES ('" + name + "', '" + email + "', '"
						+ password + "', 'Student')";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
