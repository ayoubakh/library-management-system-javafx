/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author AKH
 */
@Entity
public class EmpruntDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reference;
    private Date dateRestitutionEffective;
    private Date dateRestitutionPrevu;

    @ManyToOne
    private Livre livre;

    @ManyToOne
    private Emprunt emprunt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDateRestitutionEffective() {
        return dateRestitutionEffective;
    }

    public void setDateRestitutionEffective(Date dateRestitutionEffective) {
        this.dateRestitutionEffective = dateRestitutionEffective;
    }

    public Date getDateRestitutionPrevu() {
        return dateRestitutionPrevu;
    }

    public void setDateRestitutionPrevu(Date dateRestitutionPrevu) {
        this.dateRestitutionPrevu = dateRestitutionPrevu;
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
        if (!(object instanceof EmpruntDetail)) {
            return false;
        }
        EmpruntDetail other = (EmpruntDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.EmpruntDetail[ id=" + id + " ]";
    }

}
