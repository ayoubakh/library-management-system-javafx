package util;

import dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Util {

    public static String count(String nomClass) {
        ResultSet res = null;

        try {
            String sql = "select count(*) from " + nomClass;
            Connection connection = DBConnection.connection;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            res = preparedStatement.executeQuery();
            if (res.next()) {
                String s = res.getString("count(*)");
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    public static int getId(String table_name, String column_name, String s) {
        int id = 0;

        String sql = "SELECT id FROM" + table_name + "WHERE" + column_name + "= ?'";

        try {
            Connection connection = DBConnection.connection;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}


