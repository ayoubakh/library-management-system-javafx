package controller;

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
import net.sf.jasperreports.engine.JRException;
import service.LivreService;
import util.AlertMaker;
import util.Report;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LivreController extends ActionEvent implements Initializable {
    @FXML
    private TableView<Livre> tableLivre;
    @FXML
    private TableColumn<Livre, Integer> col_id, col_nbrExemplaire;
    @FXML
    private TableColumn<Livre, String> col_isbn, col_titre, col_langue, col_etat, col_auteur;
    @FXML
    Button btnHome, btnAbout, btnAjouter;
    @FXML
    private TextField filterField;
    private ContextMenu contextMenu;
    MenuItem supprimer = new MenuItem("supprimer");
    MenuItem modifier = new MenuItem("modifier");
    PreparedStatement preparedStatement = null;
    Connection connection = DBConnection.connection;
    public static Livre livre;

    LivreService livreService;
    private ObservableList<Livre> livres = FXCollections.observableArrayList();


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
        Stage stage = (Stage) tableLivre.getScene().getWindow();
        if (pJ != null) {
            boolean success = pJ.printPage(tableLivre);
            if (success) {
                pJ.endJob();
            }
        }
    }

    // afficher la fenetre ajouter un livre
    @FXML
    private void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/AjouterLivre.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un livre");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // afficher la table livre
    @FXML
    public TableView<Livre> afficher() {
        try {
            //SELECT n.id, s.id, n.name, s.section
            //  FROM tbl_names n
            //  JOIN tbl_section s ON s.id = n.id
            String sql = "SELECT l.id,l.isbn,l.titre,l.langue,l.nbrExemplaire,l.etat,a.nom FROM livre l,auteur a WHERE a.id=l.auteur";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //private ObservableList<Livre> livres = FXCollections.observableArrayList();
                livres.add(new Livre(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6), rs.getString(7)));
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        col_id.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("id"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<Livre, String>("isbn"));
        col_titre.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
        col_langue.setCellValueFactory(new PropertyValueFactory<Livre, String>("langue"));
        col_nbrExemplaire.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("nbrExemplaire"));
        col_etat.setCellValueFactory(new PropertyValueFactory<Livre, String>("etat"));
        col_auteur.setCellValueFactory(new PropertyValueFactory<Livre, String>("nom"));

        tableLivre.setItems(livres);
        return tableLivre;

    }

    // rechercher
    @FXML
    public void filter() {
        FilteredList<Livre> filteredLivres = new FilteredList<>(livres, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredLivres.setPredicate(livres -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (livres.getLangue().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (livres.getTitre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Livre> sortedLivre = new SortedList<>(filteredLivres);
        sortedLivre.comparatorProperty().bind(tableLivre.comparatorProperty());
        tableLivre.setItems(sortedLivre);
    }

    @FXML
    private void supprimerLivre() {
        Livre selectedLivreToDelte = tableLivre.getSelectionModel().getSelectedItem();
        if (selectedLivreToDelte == null) {
            System.out.println("no livre selected");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("suppression livre");
            alert.setContentText("\n" +
                    "êtes-vous sûr de vouloir supprimer le livre :  " + selectedLivreToDelte.getTitre() + " ?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {

                boolean res = deleteBook(selectedLivreToDelte);

                if (res) {
                    AlertMaker.showInformationAlert("Suppression livre", "le livre est supprimé");
                    livres.remove(selectedLivreToDelte);
                } else {
                    AlertMaker.showInformationAlert("", "error");
                }

            } else {
                AlertMaker.showInformationAlert("Suppression livre", "\n" +
                        "suppression annulée");

            }
        }
    }

    public boolean deleteBook(Livre livre) {
        String sql = "DELETE FROM livre WHERE isbn =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, livre.getIsbn());
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
        modifier.setOnAction(e -> modifierLivre());
        tableLivre.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(tableLivre, t.getScreenX(), t.getScreenY());

                }
            }
        });

    }

    @FXML
    public void modifierLivre() {
        Livre selectedLivreToEdit = tableLivre.getSelectionModel().getSelectedItem();
        if (selectedLivreToEdit == null) {
            AlertMaker.showInformationAlert("Modification Livre", "selectionner un livre");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AjouterLivre.fxml"));
            Parent parent = loader.load();

            AjouterLivreController controller = (AjouterLivreController) loader.getController();
            controller.remplir(selectedLivreToEdit);
            controller.update(selectedLivreToEdit);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Modifier Livre");
            stage.setScene(new Scene(parent));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void printo() throws FileNotFoundException, JRException {

        Report.generateLivres();
    }


}
 /*  final PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {

            if (printerJob.printPage(tableLivre)) {
                printerJob.endJob();
            }
        }

       */