package controller;

import bean.Adherent;
import bean.Livre;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.AdherentService;
import service.LivreService;

import java.sql.SQLException;

public class AjouterAdherentController {
    @FXML
    ImageView imageView;
    @FXML
    private TextField cinField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private AdherentController adherentController;


    @FXML
    public void close(MouseEvent mouseEvent) {
        Stage stage;
        stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void inserer() throws SQLException {
        String cin = cinField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();

        int status = 0;
        Adherent adherent = new Adherent(cin, nom, prenom);
        status = AdherentService.save(adherent);

        if (status > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INSERER ADHERENT");
            alert.setHeaderText("Information Header");
            alert.setContentText("ADHERENT enregistré");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INSERER ADHERENT");
            alert.setHeaderText("ERROR");
            alert.setContentText("Adherent non enregistré");
            alert.showAndWait();
        }


    }

    @FXML
    public void vider() {
        String cin = cinField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        cinField.clear();
        nomField.clear();
        prenomField.clear();


    }

}
