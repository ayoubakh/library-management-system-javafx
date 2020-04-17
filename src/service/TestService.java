package service;


import dao.DBConnection;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestService {

    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;

 /*   public int getIdCin() {
        int idCin = 0;
        String sql = "SELECT id from adherent where cin= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cinField.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idCin = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCin;
    }

    public int getIdIsbn() {
        int idIsbn = 0;
        String sql = "SELECT id from livre where isbn= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, isbnField.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idIsbn = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idIsbn;
    }

  */

    public int getId(String table_name, String column_name, TextField textField) {
        int id = 0;
        String sql = "SELECT id FROM" + table_name + "WHERE" + column_name + "= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, textField.getText());
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

