package service;

import bean.Livre;
import dao.DBConnection;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivreService {

    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;


    public static int save(Livre livre, int idAuteur) {

        int status = 0;
        try {
            String sql = "INSERT INTO livre(isbn,titre,langue,nbrExemplaire,etat,auteur) VALUES(?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, livre.getIsbn());
            preparedStatement.setString(2, livre.getTitre());
            preparedStatement.setString(3, livre.getLangue());
            preparedStatement.setInt(4, livre.getNbrExemplaire());
            if (livre.getNbrExemplaire() == 0) {
                preparedStatement.setString(5, "non disponible");
            } else {
                preparedStatement.setString(5, "disponible");
            }

            preparedStatement.setInt(6, idAuteur);
            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return status;
    }

    public boolean deleteBook(Livre livre) {
        Connection connection = DBConnection.connection;
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM livre WHERE isbn =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, livre.getIsbn());
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

    public static boolean updateLivre(Livre livre) {
        String sqlUpdate = "UPDATE livre SET isbn=?, titre=?, langue=?, nbrExemplaire=? WHERE Id =?";
        Connection connection = DBConnection.connection;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, livre.getIsbn());
            preparedStatement.setString(2, livre.getTitre());
            preparedStatement.setString(3, livre.getLangue());
            preparedStatement.setInt(4, livre.getNbrExemplaire());
            preparedStatement.setInt(5, livre.getId());
            int res = preparedStatement.executeUpdate();
            return res > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getIdIsbn(TextField isbnField) {
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

}
