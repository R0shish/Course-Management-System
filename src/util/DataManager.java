package util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
