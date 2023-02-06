package util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInsertion {
    private static DatabaseManager db;

    public DataInsertion() throws SQLException {
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

}
