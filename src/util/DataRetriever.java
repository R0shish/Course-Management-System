package util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import models.course.Course;
import models.course.Module;
import models.user.Teacher;

public class DataRetriever {
    private static PreparedStatement retrieveCourses;
    private static DatabaseManager db;

    public DataRetriever() throws SQLException {
        db = DatabaseManager.getInstance();
    }

    public static ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            retrieveCourses = db.getConnection().prepareStatement("SELECT * FROM courses");
            java.sql.ResultSet rs = retrieveCourses.executeQuery();
            while (rs.next()) {
                courses.add(Course.fromSql(rs.getInt("course_id"), rs.getString("course_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Course course : courses) {
            course.setModules(getModules(course.getId()));
        }
        return courses;
    }

    public static ArrayList<Module> getAllModules() {
        ArrayList<Module> allModules = new ArrayList<Module>();
        try {
            PreparedStatement retrieveModules = db.getConnection()
                    .prepareStatement("SELECT * FROM modules");
            java.sql.ResultSet rs = retrieveModules.executeQuery();
            while (rs.next()) {
                allModules.add(Module.fromSql(rs.getInt("module_id"), rs.getString("module_name"),
                        rs.getString("module_type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allModules;
    }

    public static ArrayList<Module> getModules(int courseId) {
        ArrayList<Module> modules = new ArrayList<Module>();
        try {
            PreparedStatement retrieveModules = db.getConnection()
                    .prepareStatement("SELECT * FROM modules WHERE course_id = ?");
            retrieveModules.setInt(1, courseId);
            java.sql.ResultSet rs = retrieveModules.executeQuery();
            while (rs.next()) {
                modules.add(Module.fromSql(rs.getInt("module_id"), rs.getString("module_name"),
                        rs.getString("module_type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public static ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        try {
            PreparedStatement retrieveTeachers = db.getConnection()
                    .prepareStatement("SELECT * FROM teachers");
            java.sql.ResultSet rs = retrieveTeachers.executeQuery();
            while (rs.next()) {
                int teacherId = rs.getInt("teacher_id");
                ArrayList<Module> modulesTaught = getModulesTaughtByTeacher(teacherId);

                teachers.add(Teacher.fromSql(teacherId, rs.getString("teacher_name"),
                        rs.getString("teacher_phone"), modulesTaught));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    private static ArrayList<Module> getModulesTaughtByTeacher(int teacherId) {
        ArrayList<Module> allModules = getAllModules();
        ArrayList<Module> modulesTaught = new ArrayList<Module>();
        try {
            PreparedStatement retrieveModules = db.getConnection()
                    .prepareStatement("SELECT module_id FROM teachers_modules WHERE teacher_id = ?");
            retrieveModules.setInt(1, teacherId);
            java.sql.ResultSet rs = retrieveModules.executeQuery();
            while (rs.next()) {
                allModules.forEach(module -> {
                    try {
                        if (module.getId() == rs.getInt("module_id")) {
                            modulesTaught.add(module);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modulesTaught;
    }

}
