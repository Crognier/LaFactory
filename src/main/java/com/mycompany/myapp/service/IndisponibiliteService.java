package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Indisponibilite;
import com.mycompany.myapp.repository.IndisponibiliteRepository;

@Service
public class IndisponibiliteService {

	private final IndisponibiliteRepository indisponibiliteRepository;

	public IndisponibiliteService(IndisponibiliteRepository indisponibiliteRepository) {
		this.indisponibiliteRepository = indisponibiliteRepository;
	}

	public Indisponibilite save(Indisponibilite indisponibilite) {
		return indisponibiliteRepository.save(indisponibilite);
	}

	public Indisponibilite saveAndFlush(Indisponibilite indisponibilite) {
		return indisponibiliteRepository.saveAndFlush(indisponibilite);
	}

	public List<Indisponibilite> findAll() {
		return indisponibiliteRepository.findAll();
	}

	public Optional<Indisponibilite> findById(Long id) {
		return indisponibiliteRepository.findById(id);
	}

	public void deleteById(Long id) {
		indisponibiliteRepository.deleteById(id);
	}
}
