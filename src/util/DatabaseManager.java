package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
	public static DatabaseManager instance;
	private Connection conn;

	private DatabaseManager(String path, String username, String password) throws SQLException {
		this.conn = DriverManager.getConnection(path, username, password);
		System.out.println("Connected to database");
		createDatabase("course_management_system");
		String query = "USE course_management_system";
		executeUpdate(query);
		createAndPopulateTables();
	}

	private void createDatabase(String dbName) throws SQLException {
		try {
			executeUpdate("CREATE DATABASE " + dbName);
			System.out.println("Database created!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1007)
				System.out.println("Database exists!");
			else
				throw e;
		}
	}

	private void createAndPopulateTables() throws SQLException {
		try {
			String createAuthTableSQL = """
					CREATE TABLE auth (
						id INT AUTO_INCREMENT PRIMARY KEY,
						name VARCHAR(100),
						email VARCHAR(100),
						password VARCHAR(100),
						role VARCHAR(20))
					""";
			executeUpdate(createAuthTableSQL);
			String coursesTableSQL = """
					CREATE TABLE courses (
						course_id INT PRIMARY KEY AUTO_INCREMENT,
						course_name VARCHAR(100) NOT NULL)
					""";
			executeUpdate(coursesTableSQL);
			String insertIntoCoursesSQL = """
					INSERT INTO courses(course_name) VALUES
					  ('Bachelors in Information Technology'),
					  ('Bachelors in International Business Management'),
					  ('International Master of Business Administration');
					""";
			executeUpdate(insertIntoCoursesSQL);
			String modulesTableSQL = """
					CREATE TABLE modules (
						module_id INT PRIMARY KEY AUTO_INCREMENT,
						module_name VARCHAR(100) NOT NULL,
						module_type VARCHAR(100) NOT NULL,
						course_id INT NOT NULL,
						FOREIGN KEY (course_id) REFERENCES courses(course_id))
					""";
			executeUpdate(modulesTableSQL);
			String insertIntoModulesSQL = """
					INSERT INTO modules (module_name,module_type, course_id) VALUES
						('Computational Mathematics','core', 1),
						('Fundamentals of Computing','core', 1),
						('Embedded System Programming','core', 1),
						('Academic Skills and Team-Based Learning','optional', 1),
						('Introductory Programming and Problem Solving','optional', 1),
						('21st Century Management','core', 2),
						('Principles of Business','core', 2),
						('Project Based Learning','core', 2),
						('The Digital Business','optional', 2),
						('The Innovative Business','optional', 2),
						('The Masters Research Project','core', 3),
						('Financial Decision Making','core', 3),
						('The Masters Professional Project','core', 3),
						('Strategic Operations Management','optional', 3),
						('Strategic Global Marketing','optional', 3);
					""";
			executeUpdate(insertIntoModulesSQL);
			String studentsTableSQL = """
					CREATE TABLE students (
						student_id INT PRIMARY KEY AUTO_INCREMENT,
						student_name VARCHAR(100) NOT NULL,
						course_id INT NOT NULL,
						student_phone VARCHAR(100) NOT NULL,
						level INT NOT NULL,
						auth_id INT,
						FOREIGN KEY (course_id) REFERENCES courses(course_id),
						FOREIGN KEY (auth_id) REFERENCES auth(id))
					""";
			executeUpdate(studentsTableSQL);
			String teachersTableSQL = """
					CREATE TABLE teachers (
						teacher_id INT PRIMARY KEY AUTO_INCREMENT,
						teacher_name VARCHAR(100) NOT NULL,
						teacher_phone VARCHAR(100) NOT NULL,
						auth_id INT,
						FOREIGN KEY (auth_id) REFERENCES auth(id))
					""";
			executeUpdate(teachersTableSQL);
			String enrollmentTableSQL = """
					CREATE TABLE enrollments (
					    student_id INT NOT NULL,
					    course_id INT NOT NULL,
					    FOREIGN KEY (student_id) REFERENCES students(student_id),
					    FOREIGN KEY (course_id) REFERENCES courses(course_id),
					    PRIMARY KEY (student_id, course_id))
					""";
			executeUpdate(enrollmentTableSQL);
			String teacherModulesTableSQL = """
					CREATE TABLE teachers_modules (
						teacher_id INT NOT NULL,
						module_id INT NOT NULL,
						FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id),
						FOREIGN KEY (module_id) REFERENCES modules(module_id),
						PRIMARY KEY (teacher_id, module_id))
					""";
			executeUpdate(teacherModulesTableSQL);
			String resultTableSQL = """
					CREATE TABLE results (
					    student_id INT NOT NULL,
					    module_id INT NOT NULL,
					    marks INT NOT NULL,
					    FOREIGN KEY (student_id) REFERENCES students(student_id),
					    FOREIGN KEY (module_id) REFERENCES modules(module_id),
					    PRIMARY KEY (student_id, module_id))
					""";
			executeUpdate(resultTableSQL);
			System.out.println("Tables Created and Populated!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1050)
				System.out.println("Table exists!");
			else
				throw e;
		}
	}

	public static DatabaseManager getInstance() throws SQLException {
		if (instance == null)
			instance = new DatabaseManager("jdbc:mysql://localhost:3306", "root", "");
		return instance;
	}

	public Connection getConnection() {
		return conn;
	}

	public void executeUpdate(String sql) throws SQLException {
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.executeUpdate();
		}
	}

	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
}
