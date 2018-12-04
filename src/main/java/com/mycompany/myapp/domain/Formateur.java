package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Formateur.
 */
@Entity
@Table(name = "formateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Formateur implements Serializable {

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

    @OneToMany(mappedBy = "formateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormateurMatiere> formateurMatieres = new HashSet<>();
    @OneToMany(mappedBy = "formateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Module> modules = new HashSet<>();
    @OneToMany(mappedBy = "formateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Indisponibilite> indisponibilites = new HashSet<>();
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

    public Formateur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Formateur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getNumero() {
        return numero;
    }

    public Formateur numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public Formateur rue(String rue) {
        this.rue = rue;
        return this;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public Formateur codePostal(Integer codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public Formateur ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNumeroTelephone() {
        return numeroTelephone;
    }

    public Formateur numeroTelephone(Integer numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
        return this;
    }

    public void setNumeroTelephone(Integer numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String geteMail() {
        return eMail;
    }

    public Formateur eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Set<FormateurMatiere> getFormateurMatieres() {
        return formateurMatieres;
    }

    public Formateur formateurMatieres(Set<FormateurMatiere> formateurMatieres) {
        this.formateurMatieres = formateurMatieres;
        return this;
    }

    public Formateur addFormateurMatiere(FormateurMatiere formateurMatiere) {
        this.formateurMatieres.add(formateurMatiere);
        formateurMatiere.setFormateur(this);
        return this;
    }

    public Formateur removeFormateurMatiere(FormateurMatiere formateurMatiere) {
        this.formateurMatieres.remove(formateurMatiere);
        formateurMatiere.setFormateur(null);
        return this;
    }

    public void setFormateurMatieres(Set<FormateurMatiere> formateurMatieres) {
        this.formateurMatieres = formateurMatieres;
    }

    public Set<Module> getModules() {
        return modules;
    }

    public Formateur modules(Set<Module> modules) {
        this.modules = modules;
        return this;
    }

    public Formateur addModule(Module module) {
        this.modules.add(module);
        module.setFormateur(this);
        return this;
    }

    public Formateur removeModule(Module module) {
        this.modules.remove(module);
        module.setFormateur(null);
        return this;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    public Set<Indisponibilite> getIndisponibilites() {
        return indisponibilites;
    }

    public Formateur indisponibilites(Set<Indisponibilite> indisponibilites) {
        this.indisponibilites = indisponibilites;
        return this;
    }

    public Formateur addIndisponibilite(Indisponibilite indisponibilite) {
        this.indisponibilites.add(indisponibilite);
        indisponibilite.setFormateur(this);
        return this;
    }

    public Formateur removeIndisponibilite(Indisponibilite indisponibilite) {
        this.indisponibilites.remove(indisponibilite);
        indisponibilite.setFormateur(null);
        return this;
    }

    public void setIndisponibilites(Set<Indisponibilite> indisponibilites) {
        this.indisponibilites = indisponibilites;
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
        Formateur formateur = (Formateur) o;
        if (formateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Formateur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numero=" + getNumero() +
            ", rue='" + getRue() + "'" +
            ", codePostal=" + getCodePostal() +
            ", ville='" + getVille() + "'" +
            ", numeroTelephone=" + getNumeroTelephone() +
            ", eMail='" + geteMail() + "'" +
            "}";
    }
}
