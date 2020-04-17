package service;

import bean.Emprunt;
import dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EmprunterLivreService {
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;

    public static void emprunter(Emprunt emprunt) {
        int status = 0;
        try {
            String sql = "INSERT INTO emprunt(reference,dateEmprunt,dateRestitutionEffective,dateRestitutionPrevu,adherent,livre) VALUES(?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            //   preparedStatement.setDate(1,emprunt.getDateEmprunt());
            status = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static int updateLivreAfterEmp(int id) {
        int status = 0;
        try {
            String sql = "UPDATE livre set nbrExemplaire=nbrExemplaire-1 WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int updateLivreAfterRes(int id) {
        int status = 0;
        try {
            String sql = "UPDATE livre set nbrExemplaire=nbrExemplaire+1 WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}

