package util;

import dao.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class Report {

    public static void generateReport(String cinValue, String isbnValue) throws JRException, FileNotFoundException {

        Connection connection = DBConnection.connection;
        PreparedStatement preparedStatement = null;

        String filePath = "C:\\Users\\AKH\\Desktop\\VV\\LibrarySystemManagement\\src\\reportView\\EMPRUNTL.jrxml";
        InputStream input = new FileInputStream(new File(filePath));
        JasperDesign jasperDesign = JRXmlLoader.load(input);
        try {
            JasperReport jasper = JasperCompileManager.compileReport(jasperDesign);
            JRDataSource dataSource = new JREmptyDataSource();
            HashMap parametres = new HashMap();
            parametres.put("cinParam", cinValue);
            parametres.put("isbnParam", isbnValue);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametres, connection);
            JasperViewer.viewReport(jasperPrint, false);
            String path = "C:\\Users\\AKH\\Desktop\\JasperReports";
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\emprunt.pdf");

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public static void generateLivres() throws JRException, FileNotFoundException {

        Connection connection = DBConnection.connection;
        PreparedStatement preparedStatement = null;
        String filePath = "C:\\Users\\AKH\\Desktop\\VV\\LibrarySystemManagement\\src\\reportView\\livresjrxml.jrxml";
        InputStream input = new FileInputStream(new File(filePath));
        JasperDesign jasperDesign = JRXmlLoader.load(input);
        try {
            JasperReport jasper = JasperCompileManager.compileReport(jasperDesign);
            JRDataSource dataSource = new JREmptyDataSource();
            HashMap parametres = new HashMap();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametres, connection);
            JasperViewer.viewReport(jasperPrint, false);
            String path = "C:\\Users\\AKH\\Desktop\\JasperReports";
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\livres.pdf");

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}


