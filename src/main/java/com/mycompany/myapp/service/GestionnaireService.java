package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Gestionnaire;
import com.mycompany.myapp.repository.GestionnaireRepository;

@Service
public class GestionnaireService {

	private final GestionnaireRepository gestionnaireRepository;

	public GestionnaireService(GestionnaireRepository gestionnaireRepository) {
		this.gestionnaireRepository = gestionnaireRepository;
	}

	public Gestionnaire save(Gestionnaire gestionnaire) {
		return gestionnaireRepository.save(gestionnaire);
	}

	public Gestionnaire saveAndFlush(Gestionnaire gestionnaire) {
		return gestionnaireRepository.saveAndFlush(gestionnaire);
	}

	public List<Gestionnaire> findAll() {
		return gestionnaireRepository.findAll();
	}

	public Optional<Gestionnaire> findById(Long id) {
		return gestionnaireRepository.findById(id);
	}

	public void deleteById(Long id) {
		gestionnaireRepository.deleteById(id);
	}
}
