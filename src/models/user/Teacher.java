package models.user;

import java.util.ArrayList;
import models.course.Module;

public class Teacher extends SystemUser {
	private int id;
	private String name;
	private String phone;
	private String email;
	private ArrayList<Module> modules;

	public Teacher(int id, String name, String phone, String email, ArrayList<Module> modules) {
		super(name, "Teacher");
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.modules = modules;
	}

	public Teacher(String name) {
		super(name, "Teacher");
	}

	public static Teacher fromSql(int id, String name, String phone, String email, ArrayList<Module> modules) {
		return new Teacher(id, name, phone, email, modules);
	}

	public ArrayList<Module> getModules() {
		return modules;
	}

	public String getModuleString() {
		String moduleString = "";
		for (Module module : modules) {
			moduleString += module.getName() + ", ";
		}
		if (moduleString.length() < 3) {
			return "No modules";
		}
		return moduleString.substring(0, moduleString.length() - 2);
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

}
