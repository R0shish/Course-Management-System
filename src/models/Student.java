package models;

public class Student extends SystemUser {
	public Student(String name) {
		super(name, "Student");
	}

	void study() {
		System.out.println("Studying!");
	}
}
