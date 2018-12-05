package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Stagiaire;
import com.mycompany.myapp.repository.StagiaireRepository;

@Service
public class StagiaireService {
private final StagiaireRepository stagiaireRepository;
	
	public StagiaireService(StagiaireRepository stagiaireRepository) {
		this.stagiaireRepository = stagiaireRepository;
	}
	
	public Stagiaire save(Stagiaire videoProjecteur) {
		return stagiaireRepository.save(videoProjecteur);
	}
	
	public Stagiaire saveAndFlush(Stagiaire videoProjecteur) {
		return stagiaireRepository.saveAndFlush(videoProjecteur);
	}
	
	public List<Stagiaire> findAll() {
		return stagiaireRepository.findAll();
	}
	
	public Optional<Stagiaire> findById(Long id) {
		return stagiaireRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		stagiaireRepository.deleteById(id);
	}

}
