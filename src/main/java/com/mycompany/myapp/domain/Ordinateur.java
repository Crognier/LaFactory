package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ordinateur.
 */
@Entity
@Table(name = "ordinateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ordinateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "cout_par_jour")
    private Double coutParJour;

    @Column(name = "processeur")
    private String processeur;

    @Column(name = "ram")
    private Integer ram;

    @Column(name = "hdd")
    private Integer hdd;

    @Column(name = "achat")
    private LocalDate achat;

    @OneToMany(mappedBy = "ordinateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stagiaire> stagiaires = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public Ordinateur code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Double getCoutParJour() {
        return coutParJour;
    }

    public Ordinateur coutParJour(Double coutParJour) {
        this.coutParJour = coutParJour;
        return this;
    }

    public void setCoutParJour(Double coutParJour) {
        this.coutParJour = coutParJour;
    }

    public String getProcesseur() {
        return processeur;
    }

    public Ordinateur processeur(String processeur) {
        this.processeur = processeur;
        return this;
    }

    public void setProcesseur(String processeur) {
        this.processeur = processeur;
    }

    public Integer getRam() {
        return ram;
    }

    public Ordinateur ram(Integer ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getHdd() {
        return hdd;
    }

    public Ordinateur hdd(Integer hdd) {
        this.hdd = hdd;
        return this;
    }

    public void setHdd(Integer hdd) {
        this.hdd = hdd;
    }

    public LocalDate getAchat() {
        return achat;
    }

    public Ordinateur achat(LocalDate achat) {
        this.achat = achat;
        return this;
    }

    public void setAchat(LocalDate achat) {
        this.achat = achat;
    }

    public Set<Stagiaire> getStagiaires() {
        return stagiaires;
    }

    public Ordinateur stagiaires(Set<Stagiaire> stagiaires) {
        this.stagiaires = stagiaires;
        return this;
    }

    public Ordinateur addStagiaire(Stagiaire stagiaire) {
        this.stagiaires.add(stagiaire);
        stagiaire.setOrdinateur(this);
        return this;
    }

    public Ordinateur removeStagiaire(Stagiaire stagiaire) {
        this.stagiaires.remove(stagiaire);
        stagiaire.setOrdinateur(null);
        return this;
    }

    public void setStagiaires(Set<Stagiaire> stagiaires) {
        this.stagiaires = stagiaires;
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
        Ordinateur ordinateur = (Ordinateur) o;
        if (ordinateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordinateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ordinateur{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", coutParJour=" + getCoutParJour() +
            ", processeur='" + getProcesseur() + "'" +
            ", ram=" + getRam() +
            ", hdd=" + getHdd() +
            ", achat='" + getAchat() + "'" +
            "}";
    }
}
