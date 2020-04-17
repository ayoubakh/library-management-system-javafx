package controller;

import dao.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.Util;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StatistiqueController implements Initializable {
    @FXML
    Button btnHome, btnAbout;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    static Connection connection = DBConnection.connection;
    static PreparedStatement preparedStatement = null;
    @FXML
    Label livreLabel;
    @FXML
    Label auteurLabel;
    @FXML
    Label adherentLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        livreLabel.setText(Util.count("livre") + " LIVRES");
        auteurLabel.setText(Util.count("auteur") + " AUTEURS");
        adherentLabel.setText(Util.count("adherent") + " ADHERENTS");
        loadBarChart();
    }

    @FXML
    public void loadBarChart() {

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        try {
            String sql = "SELECT id,titre FROM livre ";

            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String nbr = count(rs.getInt(1));
                series.getData().add(new XYChart.Data<>(rs.getString(2), Integer.parseInt(nbr)));


            }
            barChart.getData().add(series);

        } catch (Exception e) {
            e.getStackTrace();

        }

    }

    public String count(int id) {
        ResultSet res = null;

        try {
            String sql = "select count(*) from emprunt Where emprunt.livre = ?";
            Connection connection = DBConnection.connection;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if (res.next()) {
                String s = res.getString("count(*)");
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

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

}
