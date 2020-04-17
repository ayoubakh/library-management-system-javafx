package controller;

import bean.Auteur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.AuteurService;

import java.sql.SQLException;

public class AjouterAuteurController {
    @FXML
    ImageView imageView;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;


    @FXML
    public void close(MouseEvent mouseEvent) {
        Stage stage;
        stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void inserer() throws SQLException {
        String nom = nomField.getText();
        String prenom = prenomField.getText();

        int status = 0;
        Auteur auteur = new Auteur(nom, prenom);
        status = AuteurService.save(auteur);

        if (status > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INSERER AUTEUR");
            alert.setHeaderText("Information Header");
            alert.setContentText("AUTEUR enregistré");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INSERER AUTEUR");
            alert.setHeaderText("ERROR");
            alert.setContentText("AUTEUR non enregistré");
            alert.showAndWait();
        }


    }

    @FXML
    public void vider() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        nomField.clear();
        prenomField.clear();


    }

}
