package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Matiere;
import com.mycompany.myapp.repository.MatiereRepository;

@Service
public class MatiereService {

	private final MatiereRepository matiereRepository;

	public MatiereService(MatiereRepository matiereRepository) {
		this.matiereRepository = matiereRepository;
	}

	public Matiere save(Matiere matiere) {
		return matiereRepository.save(matiere);
	}

	public Matiere saveAndFlush(Matiere matiere) {
		return matiereRepository.saveAndFlush(matiere);
	}

	public List<Matiere> findAll() {
		return matiereRepository.findAll();
	}

	public Optional<Matiere> findById(Long id) {
		return matiereRepository.findById(id);
	}

	public void deleteById(Long id) {
		matiereRepository.deleteById(id);
	}
}
