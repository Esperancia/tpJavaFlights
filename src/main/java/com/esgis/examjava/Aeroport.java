/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author esperancia
 */
@Entity
@Table(name = "AEROPORT", uniqueConstraints = @UniqueConstraint(columnNames={"reference"}))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Aeroport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String codePays;
    private String ville;
    private String coordonnees;
    private String commentaire;
    
    @Version
    private Timestamp version;
    
    public Aeroport() {
    }
    
    public Aeroport(String reference, String code_pays, String ville, String coordonnees, String commentaire) {
        this.reference = reference;
        this.codePays = code_pays;
        this.coordonnees = coordonnees;
        this.commentaire = commentaire;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(String coordonnees) {
        this.coordonnees = coordonnees;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
     @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aeroport)) {
            return false;
        }
        Aeroport other = (Aeroport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "examenjava.Aeroport[ id=" + id + " ]";
    }
    
}
