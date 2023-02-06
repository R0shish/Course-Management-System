package models.user;

import java.util.ArrayList;
import models.course.Module;

public class Teacher extends SystemUser {
	private int id;
	private String name;
	private String phone;
	private ArrayList<Module> modules;

	public Teacher(int id, String name, String phone, ArrayList<Module> modules) {
		super(name, "Teacher");
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.modules = modules;
	}

	public Teacher(String name) {
		super(name, "Teacher");
	}

	public static Teacher fromSql(int id, String name, String phone, ArrayList<Module> modules) {
		return new Teacher(id, name, phone, modules);
	}

	public ArrayList<Module> getModules() {
		return modules;
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

}
