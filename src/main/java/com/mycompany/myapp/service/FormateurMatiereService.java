package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.FormateurMatiere;
import com.mycompany.myapp.repository.FormateurMatiereRepository;

@Service
public class FormateurMatiereService {

	private final FormateurMatiereRepository formateurMatiereRepository;

	public FormateurMatiereService(FormateurMatiereRepository formateurMatiereRepository) {
		this.formateurMatiereRepository = formateurMatiereRepository;
	}

	public FormateurMatiere save(FormateurMatiere formateurMatiere) {
		return formateurMatiereRepository.save(formateurMatiere);
	}
	
	public FormateurMatiere saveAndFlush(FormateurMatiere formateurMatiere) {
		return formateurMatiereRepository.saveAndFlush(formateurMatiere);
	}

	public List<FormateurMatiere> findAll() {
		return formateurMatiereRepository.findAll();
	}

	public Optional<FormateurMatiere> findById(Long id) {
		return formateurMatiereRepository.findById(id);
	}

	public void deleteById(Long id) {
		formateurMatiereRepository.deleteById(id);
	}
}
