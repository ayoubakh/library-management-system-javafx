package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class AboutController {

    @FXML
    private Button btnHome;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btnHome) {
            stage = (Stage) btnHome.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/System2.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goFacebook() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.facebook.com/ayoub.view248.fsoc"));
    }

    @FXML
    private void goGithub() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/AKHADAM/LibrarySystemMangement-javafx.git"));
    }

    @FXML
    private void goTwitter() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.twitter.com"));
    }

}
