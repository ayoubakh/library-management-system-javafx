package controller;

import dao.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void onLogin() {
        if (!usernameField.getText().matches("[a-zA-Z0-9]{4,}")) {
            return;
        }
        if (passwordField.getText().isEmpty()) {
            return;
        }

        int status = DBConnection.checkLogin(usernameField.getText().trim().toLowerCase(), passwordField.getText());
        switch (status) {
            case 0: {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../view/System2.fxml"));

                } catch (IOException e) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
                }
                stage.setScene(new Scene(root != null ? root : null));
                stage.setTitle("Library Management");
            }
            break;

            case -1:
                JOptionPane.showMessageDialog(null, "Connection failed");
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "username or password wrong");
                break;
        }
    }

    @FXML
    private void go() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/System2.fxml"));

        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
        stage.setScene(new Scene(root != null ? root : null));


    }
}
