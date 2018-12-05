package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Salle;
import com.mycompany.myapp.repository.SalleRepository;

@Service
public class SalleService {
private final SalleRepository salleRepository;
	
	public SalleService(SalleRepository salleRepository) {
		this.salleRepository = salleRepository;
	}
	
	public Salle save(Salle videoProjecteur) {
		return salleRepository.save(videoProjecteur);
	}
	
	public Salle saveAndFlush(Salle videoProjecteur) {
		return salleRepository.saveAndFlush(videoProjecteur);
	}
	
	public List<Salle> findAll() {
		return salleRepository.findAll();
	}
	
	public Optional<Salle> findById(Long id) {
		return salleRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		salleRepository.deleteById(id);
	}


}
