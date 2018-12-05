package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Technicien;
import com.mycompany.myapp.domain.Technicien;
import com.mycompany.myapp.repository.TechnicienRepository;

@Service
public class TechnicienService {
	
private final TechnicienRepository technicienRepository;
	
	public TechnicienService(TechnicienRepository technicienRepository) {
        this.technicienRepository = technicienRepository;
    }
	
	public Technicien save(Technicien technicien) {
		return technicienRepository.save(technicien);
	}
	
	public Technicien saveAndFlush(Technicien videoProjecteur) {
		return technicienRepository.saveAndFlush(videoProjecteur);
	}
	
	public List<Technicien> findAll() {
		return technicienRepository.findAll();
	}
	
	public Optional<Technicien> findById(Long id) {
		return technicienRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		technicienRepository.deleteById(id);
	}
	

}
