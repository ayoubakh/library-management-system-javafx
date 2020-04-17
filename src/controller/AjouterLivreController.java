package controller;

import bean.Livre;
import dao.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.AjouterLivreService;
import service.LivreService;
import util.AlertMaker;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterLivreController implements Initializable {
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;
    @FXML
    ImageView imageView;
    @FXML
    private TextField idField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField titreField;
    @FXML
    private TextField langueField;
    @FXML
    private TextField nbExField;
    @FXML
    private Label titreWindow;
    @FXML
    private LivreController livreController;
    @FXML
    private ComboBox<String> comboAuteur;
    @FXML
    private Button btnEnregistrer;


    @FXML
    public void close(MouseEvent mouseEvent) {
        Stage stage;
        stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void inserer() throws SQLException {
        int status = 0;
        String isbn = isbnField.getText();
        String titre = titreField.getText();
        String langue = langueField.getText();
        int nbrExemplaire = Integer.parseInt(nbExField.getText());

        String nbrExemlaireString = String.valueOf(nbrExemplaire);
        if (isbn.isEmpty() || titre.isEmpty() || langue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir les champs");
            alert.showAndWait();
            return;
        }
        Livre livre = new Livre(isbn, titre, langue, nbrExemplaire);
        status = LivreService.save(livre, getIdAuteur());

        //  status = this.save(livre);
        if (status > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INSERER LIVRE");
            alert.setHeaderText("Information Header");
            alert.setContentText("Livre enregistré");
            alert.showAndWait();


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INSERER LIVRE");
            alert.setHeaderText("ERROR");
            alert.setContentText("Livre non enregistré");
            alert.showAndWait();
        }

    }

    @FXML
    public void vider() {
        String isbn = isbnField.getText();
        String titre = titreField.getText();
        String langue = langueField.getText();
        int nbrExemplaire = Integer.parseInt(nbExField.getText());
        isbnField.clear();
        titreField.clear();
        langueField.clear();
        nbExField.clear();

    }

    public void remplir(Livre livre) {
        titreWindow.setText("MODIFIER LE LIVRE");
        isbnField.setText(livre.getIsbn());
        titreField.setText(livre.getTitre());
        langueField.setText(livre.getLangue());
        nbExField.setText(String.valueOf(livre.getNbrExemplaire()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nbExField.setText("0");
        AjouterLivreService.fillCombo(comboAuteur, "Select nom From auteur");
    }

    public int getIdAuteur() {
        int auteur = 0;
        String sql = "SELECT id from auteur where nom= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, comboAuteur.getSelectionModel().getSelectedItem());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                auteur = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auteur;

    }

    public void update(Livre livre) {
        btnEnregistrer.setOnAction(e -> {
            int idisbn = LivreService.getIdIsbn(isbnField);
            String sqlUpdate = "UPDATE livre SET isbn=?, titre=?, langue=?, nbrExemplaire=? WHERE Id =?";
            Connection connection = DBConnection.connection;
            int res = 0;
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
                preparedStatement.setString(1, isbnField.getText());
                preparedStatement.setString(2, titreField.getText());
                preparedStatement.setString(3, langueField.getText());
                preparedStatement.setInt(4, Integer.parseInt(nbExField.getText()));
                preparedStatement.setInt(5, idisbn);
                res = preparedStatement.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (res > 0)
                AlertMaker.showInformationAlert("Modification", "Livre modifié");
            else AlertMaker.showInformationAlert("Modification", "Modificartion echouée");

        });
    }

    public boolean refrechTable() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LivreA.fxml"));
            Parent parent = loader.load();
            LivreController controller = (LivreController) loader.getController();
            controller.tableInitialize();
            Stage stage = new Stage();
            stage.setTitle("livre");
            stage.setScene(new Scene(parent));
            stage.show();
            return true;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

}
