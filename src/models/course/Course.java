package models.course;

import java.util.ArrayList;

public class Course {
    private int id;
    private String name;
    private ArrayList<Module> modules;

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public String getModulesString() {
        String modulesString = "";
        for (Module module : modules) {
            modulesString += module.getName() + ", ";
        }
        return modulesString.substring(0, modulesString.length() - 2);
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Course fromSql(int id, String name) {
        return new Course(id, name);
    }
}
