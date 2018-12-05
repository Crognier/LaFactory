package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Objects;

/**
 * A Cursus.
 */
@Entity
@Table(name = "cursus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cursus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "date_debut")
	private LocalDate dateDebut;

	@Column(name = "duree")
	private Integer duree;

	@OneToMany(mappedBy = "cursus")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Stagiaire> stagiaires = new HashSet<>();
	@OneToMany(mappedBy = "cursus")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Module> modules = new HashSet<>();
	@ManyToOne
	@JsonIgnoreProperties("cursuses")
	private Gestionnaire gestionnaire;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public Cursus dateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
		return this;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Integer getDuree() {
		return duree;
	}

	public Cursus duree() {
		Integer duree = 0;
		Iterator<Module> iterator = this.modules.iterator();
		while (iterator.hasNext()) {
			duree += iterator.next().getDuree();
		}
		this.duree = duree;
		return this;
	}

	public void setDuree() {
		Integer duree = 0;
		Iterator<Module> iterator = this.modules.iterator();
		while (iterator.hasNext()) {
			duree += iterator.next().getDuree();
		}
		this.duree = duree;
	}

	public Set<Stagiaire> getStagiaires() {
		return stagiaires;
	}

	public Cursus stagiaires(Set<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
		return this;
	}

	public Cursus addStagiaire(Stagiaire stagiaire) {
		this.stagiaires.add(stagiaire);
		stagiaire.setCursus(this);
		return this;
	}

	public Cursus removeStagiaire(Stagiaire stagiaire) {
		this.stagiaires.remove(stagiaire);
		stagiaire.setCursus(null);
		return this;
	}

	public void setStagiaires(Set<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public Cursus modules(Set<Module> modules) {
		this.modules = modules;
		return this;
	}

	public Cursus addModule(Module module) {
		this.modules.add(module);
		module.setCursus(this);
		return this;
	}

	public Cursus removeModule(Module module) {
		this.modules.remove(module);
		module.setCursus(null);
		return this;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public Gestionnaire getGestionnaire() {
		return gestionnaire;
	}

	public Cursus gestionnaire(Gestionnaire gestionnaire) {
		this.gestionnaire = gestionnaire;
		return this;
	}

	public void setGestionnaire(Gestionnaire gestionnaire) {
		this.gestionnaire = gestionnaire;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Cursus cursus = (Cursus) o;
		if (cursus.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), cursus.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Cursus{" + "id=" + getId() + ", dateDebut='" + getDateDebut() + "'" + ", duree=" + getDuree() + "}";
	}
}
