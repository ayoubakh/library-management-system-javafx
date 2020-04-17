package controller;

import bean.Emprunt;
import dao.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import service.AdherentService;
import service.EmpruntService;
import service.EmprunterLivreService;
import service.LivreService;
import util.AlertMaker;
import util.Report;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class EmpruntController implements Initializable {

    @FXML
    Button btnHome, btnAbout;
    @FXML
    private Text cinText, nomText, prenomText, etatText, cinText1, nomText1, prenomText1;
    @FXML
    private Text isbnText, titreText, etatLivreText, isbnText1, titreText1, etatLivreText1;
    @FXML
    VBox vboxAdherent, vboxAdherent1;
    @FXML
    HBox hboxLivre, hbid, hbButton, hbDate, hbid1, hboxLivre1, hbDate1;
    @FXML
    private Button btnIdentifierAdh, getBtnIdentifierLv, btnLivre, btnLivre1;
    @FXML
    TextField cinField, isbnField, cinField1, isbnField1;
    @FXML
    private DatePicker p_dateEmprunt, p_dateRes;
    @FXML
    private DatePicker p_dateResPr;


    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;
    @FXML
    private TableView<Emprunt> tableLEmprunt;
    @FXML
    private TableColumn<Emprunt, Integer> col_id;
    @FXML
    private TableColumn<Emprunt, String> col_reference;
    @FXML
    private TableColumn<Emprunt, String> col_cinAdh;
    @FXML
    private TableColumn<Emprunt, String> col_isbnLivre;
    @FXML
    private TableColumn<Emprunt, Date> col_dateEmprunt, col_dateResEff, col_dateResPrv;
    private ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();
    @FXML
    private Button btnEmprunter, btnAnnuler, btnIdentifierAdh1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficher();
        setVisibleFalse();
    }

    @FXML
    public TableView<Emprunt> afficher() {
        try {
            String sql = "SELECT e.id,e.reference,e.dateEmprunt,e.dateRestitutionEffective,e.dateRestitutionPrevu,a.cin,l.isbn FROM emprunt e, adherent a, livre l WHERE a.id=e.adherent AND l.id=e.livre ORDER BY id";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                emprunts.add(new Emprunt(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        col_id.setCellValueFactory(new PropertyValueFactory<Emprunt, Integer>("id"));
        col_reference.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("reference"));
        col_dateEmprunt.setCellValueFactory(new PropertyValueFactory<Emprunt, Date>("dateEmprunt"));
        col_dateResEff.setCellValueFactory(new PropertyValueFactory<Emprunt, Date>("dateRestitutionEffective"));
        col_dateResPrv.setCellValueFactory(new PropertyValueFactory<Emprunt, Date>("dateRestitutionPrevu"));
        col_cinAdh.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("cinAdh"));
        col_isbnLivre.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("isbnLivre"));
        tableLEmprunt.setItems(emprunts);
        return tableLEmprunt;

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

    public void identifierAdherent(ActionEvent e) throws SQLException {
        if (e.getSource() == btnIdentifierAdh) {
            if (cinField.getText().isEmpty()) AlertMaker.showErrorAlert("Error", "Vous devez entrez le CIN");
            else {

                if (EmpruntService.identifierAdherent(cinField, cinText, nomText, prenomText) != 0) {
                    vboxAdherent.setVisible(true);
                    hbid.setVisible(true);
                } else {
                    AlertMaker.showErrorAlert("Error", "L'adhérent n'existe pas");
                }

            }
        } else if (e.getSource() == btnIdentifierAdh1) {
            if (cinField1.getText().isEmpty()) AlertMaker.showErrorAlert("Error", "Vous devez entrez le CIN");
            else {

                if (EmpruntService.identifierAdherent(cinField1, cinText1, nomText1, prenomText1) != 0) {
                    vboxAdherent1.setVisible(true);
                    hbid1.setVisible(true);
                } else {
                    AlertMaker.showErrorAlert("Error", "L'adhérent n'existe pas");
                }
            }

        }
    }

    public void identifierLivre(ActionEvent e) throws SQLException {
        if (e.getSource() == btnLivre) {
            if (isbnField.getText().isEmpty()) AlertMaker.showErrorAlert("Error", "Vous devez entrez l'ISBN");
            else {
                if (EmpruntService.identifierLivre(isbnField, isbnText, titreText, etatLivreText) != 0) {
                    hboxLivre.setVisible(true);
                    hbButton.setVisible(true);
                    hbDate.setVisible(true);
                    EmpruntService.identifierLivre(isbnField, isbnText, titreText, etatLivreText);
                } else {
                    AlertMaker.showErrorAlert("Error", "Le livre n'existe pas");
                }
            }

        } else if (e.getSource() == btnLivre1) {
            if (isbnField1.getText().isEmpty()) AlertMaker.showErrorAlert("Error", "Vous devez entrez l'ISBN");
            else {
                if (EmpruntService.identifierLivre(isbnField1, isbnText1, titreText1, etatLivreText) != 0) {
                    hboxLivre1.setVisible(true);
                    hbDate1.setVisible(true);
                    EmpruntService.identifierLivre(isbnField1, isbnText1, titreText1, etatLivreText);
                } else {
                    AlertMaker.showErrorAlert("Error", "Le livre n'existe pas");
                }
            }

        }

    }

    @FXML
    public void click(ActionEvent e) {
        if (e.getSource() == btnAnnuler) {
            cinField.setText("");
            isbnField.setText("");
            setVisibleFalse();
        } else if (e.getSource() == btnEmprunter) {
            // emprunter();
        }
    }


    @FXML
    public void setEmprunt() throws JRException, FileNotFoundException {
        String dateEmprunt = p_dateEmprunt.getEditor().getText();
        String dateResPrv = p_dateResPr.getEditor().getText();
        int idCin = AdherentService.getIdCin(cinField);
        int idisbn = LivreService.getIdIsbn(isbnField);
        int status = 0;
        int n = 0;
        try {
            String sql = "INSERT INTO emprunt(reference,dateEmprunt,dateRestitutionPrevu,dateRestitutionEffective,adherent,livre) VALUES(?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Emp " + idCin + "/" + idisbn);
            preparedStatement.setString(2, dateEmprunt);
            preparedStatement.setString(3, dateResPrv);
            preparedStatement.setString(4, "");
            preparedStatement.setInt(5, idCin);
            preparedStatement.setInt(6, idisbn);
            status = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (status > 0) {
            AlertMaker.showInformationAlert("Emprunt", "Livre empruntée");
            EmprunterLivreService.updateLivreAfterEmp(idisbn);
            Report.generateReport(cinField.getText(), isbnField.getText());
        } else {
            AlertMaker.showErrorAlert("Emprunt", "Livre non empruntée");
        }
    }

    @FXML
    public void restituer() {
        String dateRes = p_dateRes.getEditor().getText();
        int idCin = AdherentService.getIdCin(cinField1);
        int idisbn = LivreService.getIdIsbn(isbnField1);
        int status = 0;
        int n = 0;
        try {
            String sql = "UPDATE emprunt SET dateRestitutionEffective=?  WHERE  emprunt.adherent = ? AND emprunt.livre= ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dateRes);
            preparedStatement.setInt(2, idCin);
            preparedStatement.setInt(3, idisbn);
            status = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (status > 0) {
            AlertMaker.showInformationAlert("Emprunt", "Livre restituée");
            EmprunterLivreService.updateLivreAfterRes(idisbn);
        } else {
            AlertMaker.showErrorAlert("Emprunt", "Livre non restituée");
        }
    }


    public void setVisibleFalse() {
        vboxAdherent.setVisible(false);
        vboxAdherent1.setVisible(false);
        hboxLivre.setVisible(false);
        hboxLivre1.setVisible(false);
        hbid.setVisible(false);
        hbid1.setVisible(false);
        hbButton.setVisible(false);
        hbDate.setVisible(false);
        hbDate1.setVisible(false);
    }


}
