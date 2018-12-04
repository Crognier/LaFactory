package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Niveau;

/**
 * A Stagiaire.
 */
@Entity
@Table(name = "stagiaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stagiaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "rue")
    private String rue;

    @Column(name = "code_postal")
    private Integer codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "numero_telephone")
    private Integer numeroTelephone;

    @Column(name = "e_mail")
    private String eMail;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau")
    private Niveau niveau;

    @ManyToOne
    @JsonIgnoreProperties("stagiaires")
    private Ordinateur ordinateur;

    @ManyToOne
    @JsonIgnoreProperties("stagiaires")
    private Cursus cursus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Stagiaire nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Stagiaire prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getNumero() {
        return numero;
    }

    public Stagiaire numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public Stagiaire rue(String rue) {
        this.rue = rue;
        return this;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public Stagiaire codePostal(Integer codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public Stagiaire ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNumeroTelephone() {
        return numeroTelephone;
    }

    public Stagiaire numeroTelephone(Integer numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
        return this;
    }

    public void setNumeroTelephone(Integer numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String geteMail() {
        return eMail;
    }

    public Stagiaire eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Stagiaire niveau(Niveau niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Ordinateur getOrdinateur() {
        return ordinateur;
    }

    public Stagiaire ordinateur(Ordinateur ordinateur) {
        this.ordinateur = ordinateur;
        return this;
    }

    public void setOrdinateur(Ordinateur ordinateur) {
        this.ordinateur = ordinateur;
    }

    public Cursus getCursus() {
        return cursus;
    }

    public Stagiaire cursus(Cursus cursus) {
        this.cursus = cursus;
        return this;
    }

    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stagiaire stagiaire = (Stagiaire) o;
        if (stagiaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stagiaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stagiaire{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numero=" + getNumero() +
            ", rue='" + getRue() + "'" +
            ", codePostal=" + getCodePostal() +
            ", ville='" + getVille() + "'" +
            ", numeroTelephone=" + getNumeroTelephone() +
            ", eMail='" + geteMail() + "'" +
            ", niveau='" + getNiveau() + "'" +
            "}";
    }
}
