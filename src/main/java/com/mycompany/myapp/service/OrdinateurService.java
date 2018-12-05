package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Ordinateur;
import com.mycompany.myapp.repository.OrdinateurRepository;

@Service
public class OrdinateurService {
private final OrdinateurRepository ordinateurRepository;
	
	public OrdinateurService(OrdinateurRepository ordinateurRepository) {
		this.ordinateurRepository = ordinateurRepository;
	}
	
	public Ordinateur save(Ordinateur videoProjecteur) {
		return ordinateurRepository.save(videoProjecteur);
	}
	
	public Ordinateur saveAndFlush(Ordinateur videoProjecteur) {
		return ordinateurRepository.saveAndFlush(videoProjecteur);
	}
	
	public List<Ordinateur> findAll() {
		return ordinateurRepository.findAll();
	}
	
	public Optional<Ordinateur> findById(Long id) {
		return ordinateurRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		ordinateurRepository.deleteById(id);
	}

}
