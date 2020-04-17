package service;

import bean.Emprunt;
import dao.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpruntService {
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;
    private TableView<Emprunt> tableLEmprunt;
    private TableColumn<Emprunt, Integer> col_id;
    private TableColumn<Emprunt, String> col_reference, col_cin, col_isbn;
    private TableColumn<Emprunt, String> col_dateEmprunt;
    private TableColumn<Emprunt, String> col_dateResEff;
    private TableColumn<Emprunt, String> col_dateResPrv;
    private ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();

    public TableView<Emprunt> afficher() {
        try {
            String sql = "SELECT id,reference,dateEmprunt,dateRestitutionEffective,dateRestitutionPrevue from emprunt";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                emprunts.add(new Emprunt(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5)));
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        col_id.setCellValueFactory(new PropertyValueFactory<Emprunt, Integer>("id"));
        col_reference.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("reference"));
        col_dateEmprunt.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("dateEmprunt"));
        col_dateResEff.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("dateRestitutionEffective"));
        col_dateResPrv.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("dateRestitutionPrevue"));

        tableLEmprunt.setItems(emprunts);
        return tableLEmprunt;

    }
    /*int nbItem = 0;

  if (resultset!=null) {
    resultset.last();
    nbItem = resultset.getRow();
    resultset.beforeFirst();
  }
  return nbItem;

     */

    public static int identifierAdherent(TextField cinField, Text cinText, Text nomText, Text prenomText) throws SQLException {
        int nbrItem = 0;
        String sql = "SELECT cin,nom,prenom FROM adherent WHERE cin= ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cinField.getText());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cinText.setText(rs.getString(1));
                nomText.setText(rs.getString(2));
                prenomText.setText(rs.getString(3));
            }
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

    public static int identifierLivre(TextField isbnField, Text isbnText, Text titreText, Text etatText) throws SQLException {
        int nbrItem = 0;
        String sql = "SELECT isbn,titre,etat FROM livre WHERE isbn= ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, isbnField.getText());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                isbnText.setText(rs.getString(1));
                titreText.setText(rs.getString(2));
                etatText.setText(rs.getString(3));

            }
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


}
