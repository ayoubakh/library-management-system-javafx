package service;

import bean.Adherent;
import dao.DBConnection;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdherentService {
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;


    public static int save(Adherent adherent) {

        int status = 0;
        try {
            String sql = "INSERT INTO adherent(cin,nom,prenom) VALUES(?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, adherent.getCin());
            preparedStatement.setString(2, adherent.getNom());
            preparedStatement.setString(3, adherent.getPrenom());
            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return status;
    }

    public static int chercherEmprunt(TextField cinField) {
        int nbrItem = 0;
        String sql = "SELECT cin FROM adherent WHERE cin= ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cinField.getText());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                rs.last();
                nbrItem = rs.getRow();
                rs.beforeFirst();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nbrItem;
    }

    public boolean deleteAdherent(Adherent adherent) {
        Connection connection = DBConnection.connection;
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM adherent WHERE cin =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, adherent.getCin());
            int res = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (res == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.getMessage();
        }
        return false;
    }

    public static int getIdCin(TextField cinField) {
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

}
