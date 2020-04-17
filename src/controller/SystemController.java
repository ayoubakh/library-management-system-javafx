package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemController implements Initializable {
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAbout;
    @FXML
    private Button btnLivres;
    @FXML
    private Button btnAuteurs;
    @FXML
    private Button btnAdherents;
    @FXML
    private Button btnEmprunts;
    @FXML
    private Button btnStatistique;

    @FXML
    Label livreLabel;
    @FXML
    Label auteurLabel;
    @FXML
    Label adherentLabel;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btnHome) {
            stage = (Stage) btnHome.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/System2.fxml"));
            stage.setTitle("Acceuil");
        } else if (event.getSource() == btnAbout) {
            stage = (Stage) btnAbout.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/About.fxml"));
            stage.setTitle("About");

        } else if (event.getSource() == btnLivres) {
            stage = (Stage) btnLivres.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/LivreA.fxml"));
            stage.setTitle("Livres");
        } else if (event.getSource() == btnAuteurs) {
            stage = (Stage) btnAuteurs.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/Auteur.fxml"));
            stage.setTitle("Auteurs");
        } else if (event.getSource() == btnAdherents) {
            stage = (Stage) btnAdherents.getScene().getWindow();
            stage.setTitle("Adhérent");
            root = FXMLLoader.load(getClass().getResource("../view/Adherent.fxml"));
        } else if (event.getSource() == btnEmprunts) {
            stage = (Stage) btnEmprunts.getScene().getWindow();
            stage.setTitle("Emprunt");
            root = FXMLLoader.load(getClass().getResource("../view/Emprunt.fxml"));
            //  stage.setTitle("Gestion des emprunts");
        } else {
            stage = (Stage) btnStatistique.getScene().getWindow();
            stage.setTitle("Statistique");
            root = FXMLLoader.load(getClass().getResource("../view/Statistique.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void onClose() {
        Platform.exit();
    }

    @FXML
    private void signOut() {
        Stage stage = (Stage) btnHome.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/Lg.fxml"));

        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
        stage.setScene(new Scene(root != null ? root : null));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        livreLabel.setText(Util.count("livre") + " LIVRES");
        auteurLabel.setText(Util.count("auteur") + " AUTEURS");
        adherentLabel.setText(Util.count("adherent") + " ADHERENTS");

    }

}
