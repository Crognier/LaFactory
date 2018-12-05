package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Formateur;
import com.mycompany.myapp.repository.FormateurRepository;

@Service
public class FormateurService {

	private final FormateurRepository formateurRepository;

	public FormateurService(FormateurRepository formateurRepository) {
		this.formateurRepository = formateurRepository;
	}

	public Formateur save(Formateur formateur) {
		return formateurRepository.save(formateur);
	}

	public Formateur saveAndFlush(Formateur formateur) {
		return formateurRepository.saveAndFlush(formateur);
	}

	public List<Formateur> findAll() {
		return formateurRepository.findAll();
	}

	public Optional<Formateur> findById(Long id) {
		return formateurRepository.findById(id);
	}

	public void deleteById(Long id) {
		formateurRepository.deleteById(id);
	}
}
