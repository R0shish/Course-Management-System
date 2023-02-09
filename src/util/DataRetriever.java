package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.course.Course;
import models.course.Module;
import models.user.Student;
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

    public static Course getCourses(int courseId) throws SQLException {
        try {
            String sql = "SELECT * FROM courses WHERE course_id = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Course course = Course.fromSql(rs.getInt("course_id"), rs.getString("course_name"));
                course.setModules(getModules(course.getId()));
                return course;
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
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

    public static Module getModuleById(int moduleId) {
        ArrayList<Module> allModules = getAllModules();
        for (Module module : allModules) {
            if (module.getId() == moduleId) {
                return module;
            }
        }
        return null;
    }

    public static ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        try {
            PreparedStatement retrieveTeachers = db.getConnection()
                    .prepareStatement(
                            "SELECT teachers.teacher_id, teachers.teacher_name, teachers.teacher_phone, auth.email FROM teachers LEFT JOIN auth ON teachers.auth_id = auth.id");
            java.sql.ResultSet rs = retrieveTeachers.executeQuery();
            while (rs.next()) {
                int teacherId = rs.getInt("teacher_id");
                ArrayList<Module> modulesTaught = getModulesTaughtByTeacher(teacherId);

                teachers.add(Teacher.fromSql(teacherId, rs.getString("teacher_name"),
                        rs.getString("teacher_phone"), rs.getString("email"), modulesTaught));
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

    public static ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            PreparedStatement retrieveStudents = db.getConnection()
                    .prepareStatement(
                            "SELECT students.student_id, students.student_name, students.student_phone, students.level, students.course_id, auth.email FROM students LEFT JOIN auth ON students.auth_id = auth.id");
            java.sql.ResultSet rs = retrieveStudents.executeQuery();
            while (rs.next()) {
                int enrolledCourseId = rs.getInt("course_id");
                ArrayList<Course> allCourses = DataRetriever.getCourses();
                Course enrolledCourse = null;
                for (Course course : allCourses) {
                    if (course.getId() == enrolledCourseId) {
                        enrolledCourse = course;
                    }
                }
                students.add(Student.fromSql(rs.getInt("student_id"), rs.getString("student_name"),
                        rs.getString("student_phone"), rs.getString("email"), rs.getInt("level"), enrolledCourse));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static Student getStudents(int studentId) {
        try {
            PreparedStatement retrieveStudents = db.getConnection()
                    .prepareStatement(
                            "SELECT students.student_id, students.student_name, students.student_phone, students.level, students.course_id, auth.email FROM students LEFT JOIN auth ON students.auth_id = auth.id WHERE student_id = ?");
            retrieveStudents.setInt(1, studentId);
            java.sql.ResultSet rs = retrieveStudents.executeQuery();
            if (rs.next()) {
                int enrolledCourseId = rs.getInt("course_id");
                ArrayList<Course> allCourses = DataRetriever.getCourses();
                Course enrolledCourse = null;
                for (Course course : allCourses) {
                    if (course.getId() == enrolledCourseId) {
                        enrolledCourse = course;
                    }
                }

                return Student.fromSql(rs.getInt("student_id"), rs.getString("student_name"),
                        rs.getString("student_phone"), rs.getString("email"), rs.getInt("level"), enrolledCourse);
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
