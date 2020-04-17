package bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author AKH
 */
@Entity
public class Livre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String isbn;
    private String titre;
    private String langue;
    private int nbrExemplaire;
    private String nom;
    //disponible ou indisponible
    private String etat;


    public Livre(String isbn, String titre, String langue, int nbrExemplaire) {
        this.isbn = isbn;
        this.titre = titre;
        this.langue = langue;
        this.nbrExemplaire = nbrExemplaire;

    }

    public Livre(String isbn, String titre, String langue, int nbrExemplaire, String etat) {
        this.isbn = isbn;
        this.titre = titre;
        this.langue = langue;
        this.nbrExemplaire = nbrExemplaire;
        this.etat = etat;
    }

    public Livre(Integer id, String isbn, String titre, String langue, int nbrExemplaire, String etat, String nom) {
        this.id = id;
        this.isbn = isbn;
        this.titre = titre;
        this.langue = langue;
        this.nbrExemplaire = nbrExemplaire;
        this.etat = etat;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getNbrExemplaire() {
        return nbrExemplaire;
    }

    public void setNbrExemplaire(int nbrExemplaire) {
        this.nbrExemplaire = nbrExemplaire;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        if (this.nbrExemplaire == 0) {
            this.etat = "non disponible";
        } else {
            this.etat = "disponible";
        }

    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livre)) {
            return false;
        }
        Livre other = (Livre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Livre[ id=" + id + " ]";
    }


}
