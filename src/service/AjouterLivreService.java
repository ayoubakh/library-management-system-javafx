package service;

import dao.DBConnection;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AjouterLivreService {
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;

    public static void fillCombo(ComboBox<String> comboBox, String sql) {
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                comboBox.getItems().addAll(rs.getString("nom"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
