package controller;

import bean.Adherent;
import bean.Livre;
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
import service.AdherentService;
import util.AlertMaker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdherentController extends ActionEvent implements Initializable {
    @FXML
    private TableView<Adherent> tableAdherent;
    @FXML
    private TableColumn<Adherent, Integer> col_id;
    @FXML
    private TableColumn<Adherent, String> col_cin, col_nom, col_prenom;
    @FXML
    Button btnHome, btnAbout, btnAjouter;
    @FXML
    private TextField filterField;
    private ContextMenu contextMenu;
    MenuItem supprimer = new MenuItem("supprimer");
    MenuItem modifier = new MenuItem("modifier");
    PreparedStatement preparedStatement = null;
    Connection connection = DBConnection.connection;

    AdherentService adherentService;
    private ObservableList<Adherent> adherents = FXCollections.observableArrayList();


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
        Stage stage = (Stage) tableAdherent.getScene().getWindow();
        if (pJ != null) {
            boolean success = pJ.printPage(tableAdherent);
            if (success) {
                pJ.endJob();
            }
        }
    }

    // afficher la fenetre ajouter un livre
    @FXML
    private void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/AjouterAdherent.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un adhérent");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getEmprunts() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/ChercherEmpruntAdherent.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Trouver les emprunts");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // afficher la table adherent
    @FXML
    public TableView<Adherent> afficher() {
        try {
            String sql = "SELECT id,cin,nom,prenom FROM adherent";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                adherents.add(new Adherent(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4)));
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        col_id.setCellValueFactory(new PropertyValueFactory<Adherent, Integer>("id"));
        col_cin.setCellValueFactory(new PropertyValueFactory<Adherent, String>("cin"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Adherent, String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<Adherent, String>("prenom"));

        tableAdherent.setItems(adherents);
        return tableAdherent;

    }

    // rechercher
    @FXML
    public void filter() {
        FilteredList<Adherent> filteredAdherent = new FilteredList<>(adherents, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAdherent.setPredicate(adherents -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (adherents.getCin().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (adherents.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Adherent> sortedAdherent = new SortedList<>(filteredAdherent);
        sortedAdherent.comparatorProperty().bind(tableAdherent.comparatorProperty());
        tableAdherent.setItems(sortedAdherent);
    }

    @FXML
    private void supprimerLivre() {
        Adherent selectedAdherentToDelte = tableAdherent.getSelectionModel().getSelectedItem();
        if (selectedAdherentToDelte == null) {
            System.out.println("no Adherent selected");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("suppression Adherent");
            alert.setContentText("\n" +
                    "êtes-vous sûr de vouloir supprimer cet Adherent :  " + selectedAdherentToDelte.getNom() + " ?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {

                boolean res = deleteAdherent(selectedAdherentToDelte);

                if (res) {
                    AlertMaker.showInformationAlert("Suppression Adherent", "l'adherent est supprimé");
                    adherents.remove(selectedAdherentToDelte);
                } else {
                    AlertMaker.showInformationAlert("", "error");
                }

            } else {
                AlertMaker.showInformationAlert("Suppression adherent", "\n" +
                        "suppression annulée");

            }
        }
    }

    public boolean deleteAdherent(Adherent adherent) {
        String sql = "DELETE FROM adherent WHERE cin =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, adherent.getCin());
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
        supprimer.setOnAction(e -> supprimerLivre());
        tableAdherent.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(tableAdherent, t.getScreenX(), t.getScreenY());

                }
            }
        });

    }

}
