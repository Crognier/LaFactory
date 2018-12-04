package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Niveau;

/**
 * A FormateurMatiere.
 */
@Entity
@Table(name = "formateur_matiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormateurMatiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau")
    private Niveau niveau;

    @ManyToOne
    @JsonIgnoreProperties("formateurMatieres")
    private Formateur formateur;

    @ManyToOne
    @JsonIgnoreProperties("formateurMatieres")
    private Matiere matiere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public FormateurMatiere niveau(Niveau niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public FormateurMatiere formateur(Formateur formateur) {
        this.formateur = formateur;
        return this;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public FormateurMatiere matiere(Matiere matiere) {
        this.matiere = matiere;
        return this;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
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
        FormateurMatiere formateurMatiere = (FormateurMatiere) o;
        if (formateurMatiere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formateurMatiere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormateurMatiere{" +
            "id=" + getId() +
            ", niveau='" + getNiveau() + "'" +
            "}";
    }
}
