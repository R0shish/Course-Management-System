package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.user.Student;

public class DataManager {
    private static DatabaseManager db;

    public DataManager() throws SQLException {
        db = DatabaseManager.getInstance();
    }

    public static void insert(String table, String[] columns, String[] values) throws SQLException {
        String sql = "INSERT INTO " + table + " (";
        for (int i = 0; i < columns.length; i++) {
            sql += columns[i];
            if (i < columns.length - 1) {
                sql += ", ";
            }
        }
        sql += ") VALUES (";
        for (int i = 0; i < values.length; i++) {
            sql += "?";
            if (i < values.length - 1) {
                sql += ", ";
            }
        }
        sql += ")";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            ps.setString(i + 1, values[i]);
        }
        ps.executeUpdate();
    }

    public static void deleteCourse(String id) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();
    }

    public static void editCourse(String id, String name) throws SQLException {
        String sql = "UPDATE courses SET course_name = ? WHERE course_id = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, Integer.parseInt(id));
        ps.executeUpdate();
    }

    public static void addTeacher(String name, String phoneNumber, String email, String password) throws SQLException {
        String insertAuth = "INSERT INTO auth (name, email, password, role) VALUES (?, ?, ?, 'Teacher')";
        PreparedStatement stmtAuth;

        stmtAuth = db.getConnection().prepareStatement(insertAuth, Statement.RETURN_GENERATED_KEYS);
        stmtAuth.setString(1, name);
        stmtAuth.setString(2, email);
        stmtAuth.setString(3, password);
        stmtAuth.executeUpdate();

        ResultSet generatedKeys = stmtAuth.getGeneratedKeys();
        int authId = -1;
        if (generatedKeys.next()) {
            authId = generatedKeys.getInt(1);
        }

        String insertTeacher = "INSERT INTO teachers (teacher_name, teacher_phone, auth_id) VALUES (?, ?, ?)";
        PreparedStatement stmtTeacher = db.getConnection().prepareStatement(insertTeacher);
        stmtTeacher.setString(1, name);
        stmtTeacher.setString(2, phoneNumber);
        stmtTeacher.setInt(3, authId);
        stmtTeacher.executeUpdate();
    }

    public static void editTeacher(String id, String name, String phoneNumber, String email, String password)
            throws SQLException {
        try {
            int teacherId = Integer.parseInt(id);

            // Update information in the teachers table
            StringBuilder updateTeacherQuery = new StringBuilder("UPDATE teachers SET ");
            ArrayList<String> updates = new ArrayList<>();
            ArrayList<String> updateParams = new ArrayList<>();

            if (name != null && !name.equals("")) {
                updates.add("teacher_name = ?");
                updateParams.add(name);
            }

            if (phoneNumber != null && !phoneNumber.equals("")) {
                updates.add("teacher_phone = ?");
                updateParams.add(phoneNumber);
            }

            if (updates.size() > 0) {
                updateTeacherQuery.append(String.join(", ", updates));
                updateTeacherQuery.append(" WHERE teacher_id = ?");
                PreparedStatement updateTeacher = db.getConnection().prepareStatement(updateTeacherQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                    updateTeacher.setString(i + 1, updateParams.get(i));
                }
                updateTeacher.setInt(updateParams.size() + 1, teacherId);
                updateTeacher.executeUpdate();
            }

            // Update information in the auth table
            StringBuilder updateAuthQuery = new StringBuilder("UPDATE auth SET ");
            updates.clear();
            updateParams.clear();

            if (email != null && !email.equals("")) {
                updates.add("email = ?");
                updateParams.add(email);
            }

            if (password != null && !password.equals("")) {
                updates.add("password = ?");
                updateParams.add(password);
            }

            if (updates.size() > 0) {
                updateAuthQuery.append(String.join(", ", updates));
                updateAuthQuery.append(" WHERE id = (SELECT auth_id FROM teachers WHERE teacher_id = ?)");
                PreparedStatement updateAuth = db.getConnection().prepareStatement(updateAuthQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                    updateAuth.setString(i + 1, updateParams.get(i));
                }
                updateAuth.setInt(updateParams.size() + 1, teacherId);
                updateAuth.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void assignModulesToTeacher(String teacherId, String moduleId) throws SQLException {
        String sql = "INSERT INTO teachers_modules (teacher_id, module_id) VALUES (?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(teacherId));
        ps.setInt(2, Integer.parseInt(moduleId));
        ps.executeUpdate();
    }

    public static void unassignModulesFromTeacher(String teacherId, String moduleId) throws SQLException {
        String sql = "DELETE FROM teachers_modules WHERE teacher_id = ? AND module_id = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(teacherId));
        ps.setInt(2, Integer.parseInt(moduleId));
        ps.executeUpdate();
    }

    public static void deleteTeacher(String id) throws SQLException {

        String sql = "SELECT auth_id FROM teachers WHERE teacher_id = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int authId = rs.getInt("auth_id");

        sql = "DELETE FROM teachers WHERE teacher_id = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();

        sql = "DELETE FROM auth WHERE id = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, authId);
        ps.executeUpdate();
    }

    public static void enrollStudent(int studentId, int courseId, String phoneNumber, Student student)
            throws SQLException {
        String sql = "INSERT INTO students (student_id, student_name, student_phone, course_id, auth_id, level) VALUES (?, ?, ?, ?, ?, 4)";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, studentId);
        ps.setString(2, student.getName());
        ps.setString(3, phoneNumber);
        student.setPhone(phoneNumber);
        ps.setInt(4, courseId);
        ps.setInt(5, studentId);
        ps.executeUpdate();

        sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, studentId);
        ps.setInt(2, courseId);
        ps.executeUpdate();
    }

    public static void addStudent(String name, String phoneNumber, String email, String password, int courseId,
            int level)
            throws SQLException {
        String insertAuth = "INSERT INTO auth (name, email, password, role) VALUES (?, ?, ?, 'Student')";
        PreparedStatement stmtAuth;

        stmtAuth = db.getConnection().prepareStatement(insertAuth, Statement.RETURN_GENERATED_KEYS);
        stmtAuth.setString(1, name);
        stmtAuth.setString(2, email);
        stmtAuth.setString(3, password);
        stmtAuth.executeUpdate();

        ResultSet generatedKeys = stmtAuth.getGeneratedKeys();
        int authId = -1;
        if (generatedKeys.next()) {
            authId = generatedKeys.getInt(1);
        }

        String insertStudent = "INSERT INTO students (student_name, student_phone, course_id, level, auth_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmtStudent = db.getConnection().prepareStatement(insertStudent);
        stmtStudent.setString(1, name);
        stmtStudent.setString(2, phoneNumber);
        stmtStudent.setInt(3, courseId);
        stmtStudent.setInt(4, level);
        stmtStudent.setInt(5, authId);
        stmtStudent.executeUpdate();
    }

    public static void editStudent(String id, String name, String phoneNumber, String email, String password)
            throws SQLException {
        try {
            // Update information in the students table
            StringBuilder updateStudentQuery = new StringBuilder("UPDATE students SET ");
            ArrayList<String> updates = new ArrayList<>();
            ArrayList<String> updateParams = new ArrayList<>();

            if (name != null && !name.equals("")) {
                updates.add("student_name = ?");
                updateParams.add(name);
            }

            if (phoneNumber != null && !phoneNumber.equals("")) {
                updates.add("student_phone = ?");
                updateParams.add(phoneNumber);
            }

            if (updates.size() > 0) {
                updateStudentQuery.append(String.join(", ", updates));
                updateStudentQuery.append(" WHERE student_id = ?");
                PreparedStatement updateStudent = db.getConnection().prepareStatement(updateStudentQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                    updateStudent.setString(i + 1, updateParams.get(i));
                }
                updateStudent.setInt(updateParams.size() + 1, Integer.parseInt(id));
                updateStudent.executeUpdate();
            }

            // Update information in the auth table
            StringBuilder updateAuthQuery = new StringBuilder("UPDATE auth SET ");
            updates.clear();
            updateParams.clear();

            if (email != null && !email.equals("")) {
                updates.add("email = ?");
                updateParams.add(email);
            }

            if (password != null && !password.equals("")) {
                updates.add("password = ?");
                updateParams.add(password);
            }

            if (updates.size() > 0) {
                updateAuthQuery.append(String.join(", ", updates));
                updateAuthQuery.append(" WHERE id = (SELECT auth_id FROM students WHERE student_id = ?)");
                PreparedStatement updateAuth = db.getConnection().prepareStatement(updateAuthQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                    updateAuth.setString(i + 1, updateParams.get(i));
                }
                updateAuth.setInt(updateParams.size() + 1, Integer.parseInt(id));
                updateAuth.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void deleteStudent(String id) throws SQLException {
        String sql = "SELECT auth_id FROM students WHERE student_id = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int authId = rs.getInt("auth_id");

        sql = "DELETE FROM students WHERE student_id = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();

        sql = "DELETE FROM auth WHERE id = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, authId);
        ps.executeUpdate();
    }

    public static void markStudent(String studentId, String moduleId, String marks) throws SQLException {
        String sql = "INSERT INTO results (student_id, module_id, marks) VALUES (?, ?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(studentId));
        ps.setInt(2, Integer.parseInt(moduleId));
        ps.setInt(3, Integer.parseInt(marks));
        ps.executeUpdate();
    }

    public static int retrieveMarks(int studentId, String moduleId) throws SQLException {
        String sql = "SELECT marks FROM results WHERE student_id = ? AND module_id = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, studentId);
        ps.setInt(2, Integer.parseInt(moduleId));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int marks = rs.getInt("marks");
        return marks;
    }
}
