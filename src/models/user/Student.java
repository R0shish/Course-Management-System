package models.user;

import models.course.Course;

public class Student extends SystemUser {
	private int id;
	private String name;
	private String phone;
	private String email;
	private int level;
	private Course enrolledCourse;

	public Student(int id, String name, String phone, String email, int level, Course enrolledCourse) {
		super(name, "Student");
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.level = level;
		this.enrolledCourse = enrolledCourse;
	}

	public Student(int id, String name) {
		super(name, "Student");
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public int getLevel() {
		return level;
	}

	public Course getEnrolledCourse() {
		return enrolledCourse;
	}

	public static Student fromSql(int id, String name, String phone, String email, int level, Course enrolledCourse) {
		return new Student(id, name, phone, email, level, enrolledCourse);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setEnrolledCourse(Course enrolledCourse) {
		this.enrolledCourse = enrolledCourse;
	}

}
