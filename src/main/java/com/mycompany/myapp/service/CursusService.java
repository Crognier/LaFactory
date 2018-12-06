package com.mycompany.myapp.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Cursus;
import com.mycompany.myapp.domain.Module;
import com.mycompany.myapp.repository.CursusRepository;

@Service
public class CursusService {

	private final CursusRepository cursusRepository;

	public CursusService(CursusRepository cursusRepository) {
		this.cursusRepository = cursusRepository;
	}

	public Cursus save(Cursus cursus) {
		return cursusRepository.save(cursus);
	}

	public Cursus saveAndFlush(Cursus cursus) {
		return cursusRepository.saveAndFlush(cursus);
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
		Optional<Cursus> cursusOptional = cursusRepository.findByIdWithModules(id);
		cursusOptional.get().setDuree(calculerDuree(cursusOptional.get()));
		return cursusOptional;
	}

	public List<Cursus> findAllWithModules() {
		List<Cursus> cursuses = cursusRepository.findAllWithModules();
		for (Cursus cursus : cursuses) {
			cursus.setDuree(calculerDuree(cursus));
		}
		return cursuses;
	}

	public Integer calculerDuree(Cursus cursus) {
		Integer duree = 0;
		Iterator<Module> iterator = cursus.getModules().iterator();
		while (iterator.hasNext()) {
			duree += iterator.next().getDuree();
		}
		return duree;
	}

}
