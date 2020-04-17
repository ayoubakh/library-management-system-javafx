package controller;

import bean.Auteur;
import dao.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AuteurService;
import util.AlertMaker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AuteurController extends ActionEvent implements Initializable {
    @FXML
    private TableView<Auteur> tableAuteur;
    @FXML
    private TableColumn<Auteur, Integer> col_id;
    @FXML
    private TableColumn<Auteur, String> col_nom, col_prenom;
    @FXML
    Button btnHome, btnAbout, btnAjouter, btnLivres;
    @FXML
    private TextField filterField;
    private ContextMenu contextMenu;
    MenuItem supprimer = new MenuItem("supprimer");
    MenuItem modifier = new MenuItem("modifier");
    PreparedStatement preparedStatement = null;
    Connection connection = DBConnection.connection;

    AuteurService auteurService;
    private ObservableList<Auteur> auteurs = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableInitialize();
        afficher();
        filter();

    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btnHome) {
            stage = (Stage) btnHome.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/System2.fxml"));
        } else if (event.getSource() == btnAbout) {
            stage = (Stage) btnAbout.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/About.fxml"));

        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void print() {
        PrinterJob pJ = PrinterJob.createPrinterJob();
        Stage stage = (Stage) tableAuteur.getScene().getWindow();
        if (pJ != null) {
            boolean success = pJ.printPage(tableAuteur);
            if (success) {
                pJ.endJob();
            }
        }
    }

    // afficher la fenetre ajouter un auteur
    @FXML
    private void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/AjouterAuteur.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un auteur");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getLivres() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/ChercherLivreAuteur.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Trouver les livres");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // afficher la table auteur
    @FXML
    public TableView<Auteur> afficher() {
        try {
            String sql = "SELECT id,nom,prenom FROM auteur";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                auteurs.add(new Auteur(rs.getInt(1), rs.getString(2),
                        rs.getString(3)));
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        col_id.setCellValueFactory(new PropertyValueFactory<Auteur, Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Auteur, String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<Auteur, String>("prenom"));

        tableAuteur.setItems(auteurs);
        return tableAuteur;

    }

    // rechercher
    @FXML
    public void filter() {
        FilteredList<Auteur> filteredAuteurs = new FilteredList<>(auteurs, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAuteurs.setPredicate(auteurs -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (auteurs.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (auteurs.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Auteur> sortedLivre = new SortedList<>(filteredAuteurs);
        sortedLivre.comparatorProperty().bind(tableAuteur.comparatorProperty());
        tableAuteur.setItems(sortedLivre);
    }

    @FXML
    private void supprimerAuteur() {
        Auteur selectedAuthorToDelte = tableAuteur.getSelectionModel().getSelectedItem();
        if (selectedAuthorToDelte == null) {
            System.out.println("no author selected");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("suppression auteur");
            alert.setContentText("\n" +
                    "êtes-vous sûr de vouloir supprimer cet auteur :  " + selectedAuthorToDelte.getNom() + " ?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {

                boolean res = deleteAuthor(selectedAuthorToDelte);

                if (res) {
                    AlertMaker.showInformationAlert("Suppression auteur", "l'auteur  est supprimé");
                    auteurs.remove(selectedAuthorToDelte);
                } else {
                    AlertMaker.showInformationAlert("", "error");
                }

            } else {
                AlertMaker.showInformationAlert("Suppression auteur", "\n" +
                        "suppression annulée");

            }
        }
    }

    public boolean deleteAuthor(Auteur auteur) {
        String sql = "DELETE FROM auteur WHERE nom =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auteur.getNom());
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

    public void tableInitialize() {
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(supprimer, modifier);
        supprimer.setOnAction(e -> supprimerAuteur());
        tableAuteur.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(tableAuteur, t.getScreenX(), t.getScreenY());

                }
            }
        });

    }
}
