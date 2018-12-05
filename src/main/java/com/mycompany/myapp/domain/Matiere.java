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
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "objectifs")
    private String objectifs;

    @Column(name = "prerequis")
    private String prerequis;

    @Column(name = "contenu")
    private String contenu;

    @OneToMany(mappedBy = "matiere", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormateurMatiere> formateurMatieres = new HashSet<>();
    @OneToMany(mappedBy = "matiere", cascade= {CascadeType.PERSIST, CascadeType.REFRESH})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Module> modules = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public Matiere titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getObjectifs() {
        return objectifs;
    }

    public Matiere objectifs(String objectifs) {
        this.objectifs = objectifs;
        return this;
    }

    public void setObjectifs(String objectifs) {
        this.objectifs = objectifs;
    }

    public String getPrerequis() {
        return prerequis;
    }

    public Matiere prerequis(String prerequis) {
        this.prerequis = prerequis;
        return this;
    }

    public void setPrerequis(String prerequis) {
        this.prerequis = prerequis;
    }

    public String getContenu() {
        return contenu;
    }

    public Matiere contenu(String contenu) {
        this.contenu = contenu;
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Set<FormateurMatiere> getFormateurMatieres() {
        return formateurMatieres;
    }

    public Matiere formateurMatieres(Set<FormateurMatiere> formateurMatieres) {
        this.formateurMatieres = formateurMatieres;
        return this;
    }

    public Matiere addFormateurMatiere(FormateurMatiere formateurMatiere) {
        this.formateurMatieres.add(formateurMatiere);
        formateurMatiere.setMatiere(this);
        return this;
    }

    public Matiere removeFormateurMatiere(FormateurMatiere formateurMatiere) {
        this.formateurMatieres.remove(formateurMatiere);
        formateurMatiere.setMatiere(null);
        return this;
    }

    public void setFormateurMatieres(Set<FormateurMatiere> formateurMatieres) {
        this.formateurMatieres = formateurMatieres;
    }

    public Set<Module> getModules() {
        return modules;
    }

    public Matiere modules(Set<Module> modules) {
        this.modules = modules;
        return this;
    }

    public Matiere addModule(Module module) {
        this.modules.add(module);
        module.setMatiere(this);
        return this;
    }

    public Matiere removeModule(Module module) {
        this.modules.remove(module);
        module.setMatiere(null);
        return this;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
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
        Matiere matiere = (Matiere) o;
        if (matiere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matiere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", objectifs='" + getObjectifs() + "'" +
            ", prerequis='" + getPrerequis() + "'" +
            ", contenu='" + getContenu() + "'" +
            "}";
    }
}
