/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author AKH
 */
@Entity
public class Emprunt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String reference;
    private String dateEmprunt;
    private String dateRestitutionEffective;
    private String dateRestitutionPrevu;
    private String cinAdh;
    private String isbnLivre;
    private int cin;
    private int isbn;
    @ManyToOne
    private Adherent adherent;
    private Livre livre;

    public Emprunt(int id, String reference, String dateEmprunt, String dateRestitutionEffective, String dateRestitutionPrevu) {
        this.id = id;
        this.reference = reference;
        this.dateEmprunt = dateEmprunt;
        this.dateRestitutionEffective = dateRestitutionEffective;
        this.dateRestitutionPrevu = dateRestitutionPrevu;
    }

    public Emprunt(int id, String reference, String dateEmprunt, String dateRestitutionEffective, String dateRestitutionPrevu, String cinAdh, String isbnLivre) {
        this.id = id;
        this.reference = reference;
        this.dateEmprunt = dateEmprunt;
        this.dateRestitutionEffective = dateRestitutionEffective;
        this.dateRestitutionPrevu = dateRestitutionPrevu;
        this.cinAdh = cinAdh;
        this.isbnLivre = isbnLivre;
    }

    public Emprunt(String reference, String dateEmprunt, String dateRestitutionEffective, String dateRestitutionPrevu, String isbnLivre) {
        this.reference = reference;
        this.dateEmprunt = dateEmprunt;
        this.dateRestitutionEffective = dateRestitutionEffective;
        this.dateRestitutionPrevu = dateRestitutionPrevu;
        this.isbnLivre = isbnLivre;
    }

 /*  public Emprunt(int id, String reference, java.sql.Date dateEmprunt, String dateRestitutionEffective, String dateRestitutionPrevu) {
        this.id = id;
        this.reference = reference;
        this.dateEmprunt = dateEmprunt;
        this.dateRestitutionEffective = dateRestitutionEffective;
        this.dateRestitutionPrevu = dateRestitutionPrevu;
    }

    public Emprunt(int id, String reference,java.sql.Date dateEmprunt, java.sql.Date dateRestitutionEffective, java.sql.Date dateRestitutionPrevu, String cinAdh, String isbnLivre) {
        this.id = id;
        this.reference = reference;
        this.dateEmprunt = dateEmprunt;
        this.dateRestitutionEffective = dateRestitutionEffective;
        this.dateRestitutionPrevu = dateRestitutionPrevu;
        this.cinAdh = cinAdh;
        this.isbnLivre = isbnLivre;
    }

    public Emprunt(int id, java.sql.Date dateEmprunt, java.sql.Date dateRestitutionPrevu, int cin, int isbn) {
        this.id = id;
        this.dateEmprunt = dateEmprunt;
        this.dateRestitutionPrevu = dateRestitutionPrevu;
        this.cin = cin;
        this.isbn = isbn;
    }

    public Emprunt(java.sql.Date dateEmprunt,java.sql.Date dateRestitutionPrevu, int cin, int isbn) {
        this.dateEmprunt = dateEmprunt;
        this.dateRestitutionPrevu = dateRestitutionPrevu;
        this.cin = cin;
        this.isbn = isbn;
    }


   */


    public int getId() {
        return id;
    }


    public String getCinAdh() {
        return cinAdh;
    }

    public void setCinAdh(String cinAdh) {
        this.cinAdh = cinAdh;
    }

    public String getIsbnLivre() {
        return isbnLivre;
    }

    public void setIsbnLivre(String isbnLivre) {
        this.isbnLivre = isbnLivre;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(String dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public String getDateRestitutionEffective() {
        return dateRestitutionEffective;
    }

    public void setDateRestitutionEffective(String dateRestitutionEffective) {
        this.dateRestitutionEffective = dateRestitutionEffective;
    }

    public void setDateRestitutionPrevu(String dateRestitutionPrevu) {
        this.dateRestitutionPrevu = dateRestitutionPrevu;
    }

    public String getDateRestitutionPrevu() {
        return dateRestitutionPrevu;
    }
}

