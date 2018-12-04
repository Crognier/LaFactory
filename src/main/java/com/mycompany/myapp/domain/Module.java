package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "duree")
    private Integer duree;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Salle salle;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private VideoProjecteur videoProjecteur;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Cursus cursus;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Formateur formateur;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Matiere matiere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Module dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Integer getDuree() {
        return duree;
    }

    public Module duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Salle getSalle() {
        return salle;
    }

    public Module salle(Salle salle) {
        this.salle = salle;
        return this;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public VideoProjecteur getVideoProjecteur() {
        return videoProjecteur;
    }

    public Module videoProjecteur(VideoProjecteur videoProjecteur) {
        this.videoProjecteur = videoProjecteur;
        return this;
    }

    public void setVideoProjecteur(VideoProjecteur videoProjecteur) {
        this.videoProjecteur = videoProjecteur;
    }

    public Cursus getCursus() {
        return cursus;
    }

    public Module cursus(Cursus cursus) {
        this.cursus = cursus;
        return this;
    }

    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public Module formateur(Formateur formateur) {
        this.formateur = formateur;
        return this;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Module matiere(Matiere matiere) {
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
        Module module = (Module) o;
        if (module.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), module.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", duree=" + getDuree() +
            "}";
    }
}
