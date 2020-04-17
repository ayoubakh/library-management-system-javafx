package service;

import bean.Auteur;
import bean.Livre;
import dao.DBConnection;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuteurService {
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;


    public static int save(Auteur auteur) {

        int status = 0;
        try {
            String sql = "INSERT INTO auteur(nom,prenom) VALUES(?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auteur.getNom());
            preparedStatement.setString(2, auteur.getPrenom());
            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return status;
    }

    public static int chercherLivre(TextField nomField) {
        int nbrItem = 0;
        String sql = "SELECT nom FROM auteur WHERE nom= ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomField.getText());
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


    public boolean deleteAuthor(Auteur auteur) {
        java.sql.Connection connection = DBConnection.connection;
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM auteur WHERE nom =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auteur.getNom());
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
}
