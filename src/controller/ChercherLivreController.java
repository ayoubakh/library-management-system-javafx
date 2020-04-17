package controller;


import bean.Livre;
import dao.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AuteurService;
import util.AlertMaker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChercherLivreController implements Initializable {
    @FXML
    ImageView imageView;
    @FXML
    private Button btnAnnuler;
    @FXML
    TextField nomField;
    @FXML
    VBox vboxLivre;
    @FXML
    private Button btnChercher;
    @FXML
    private TableView<Livre> tableLivre;
    @FXML
    private TableColumn<Livre, String> col_isbn;
    @FXML
    private TableColumn<Livre, String> col_titre;
    @FXML
    private TableColumn<Livre, String> col_langue;
    @FXML
    private TableColumn<Livre, Integer> col_nbEx;
    @FXML
    private TableColumn<Livre, String> col_etat;
    private ObservableList<Livre> livres = FXCollections.observableArrayList();
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setVisibleFalse();
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        Stage stage;
        stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public TableView<Livre> afficher() {
        try {
            String sql = "SELECT a.isbn,a.titre,a.langue,a.nbrExemplaire,a.etat FROM livre a,auteur e WHERE a.auteur=e.id and e.nom=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomField.getText());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                livres.add(new Livre(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4), rs.getString(5)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        col_isbn.setCellValueFactory(new PropertyValueFactory<Livre, String>("isbn"));
        col_titre.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
        col_langue.setCellValueFactory(new PropertyValueFactory<Livre, String>("langue"));
        col_nbEx.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("nbrExemplaire"));
        col_etat.setCellValueFactory(new PropertyValueFactory<Livre, String>("etat"));
        tableLivre.setItems(livres);
        return tableLivre;

    }

    public void chercherLivre() throws SQLException {

        if (nomField.getText().isEmpty()) AlertMaker.showErrorAlert("Error", "Vous devez entrez le NOM");

        else {
            if (AuteurService.chercherLivre(nomField) == 0) {
                AlertMaker.showErrorAlert("Error", "L'auteur n'existe pas");
            } else {
                btnChercher.setDisable(true);
                vboxLivre.setVisible(true);
                afficher();

            }
        }

    }

    public void annuler(ActionEvent e) {
        if (e.getSource() == btnAnnuler) {
            nomField.setText("");
            setVisibleFalse();
        }
    }

    public void setVisibleFalse() {
        vboxLivre.setVisible(false);
        btnChercher.setDisable(false);
    }

}