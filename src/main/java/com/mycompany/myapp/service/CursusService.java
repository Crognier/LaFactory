package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Cursus;
import com.mycompany.myapp.repository.CursusRepository;

@Service
public class CursusService {

	private final CursusRepository cursusRepository;

	public CursusService(CursusRepository cursusRepository) {
		this.cursusRepository = cursusRepository;
	}

	public Cursus save(Cursus cursus) {
		return cursusRepository.save(cursus.duree());
	}

	public Cursus saveAndFlush(Cursus cursus) {
		return cursusRepository.saveAndFlush(cursus.duree());
	}

	public List<Cursus> findAll() {
		return cursusRepository.findAll();
	}

	public Optional<Cursus> findById(Long id) {
		return cursusRepository.findById(id);

	}

	public void deleteById(Long id) {
		cursusRepository.deleteById(id);
	}

	public Optional<Cursus> findByIdWithModules(Long id) {
		return cursusRepository.findByIdWithModules(id);
	}

}
