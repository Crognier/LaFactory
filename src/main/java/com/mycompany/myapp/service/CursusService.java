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
		return cursusRepository.save(calculerDuree(cursus));
	}

	public Cursus saveAndFlush(Cursus cursus) {
		return cursusRepository.saveAndFlush(calculerDuree(cursus));
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
		System.out.println(cursusRepository.findByIdWithModules(id).get().getGestionnaire().getNom());
		System.out.println(cursusRepository.findByIdWithModules(id).get().getDuree());
		System.out.println(cursusRepository.findByIdWithModules(id).get().getModules());
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		return cursusRepository.findByIdWithModules(id);
	}

	public Cursus calculerDuree(Cursus cursus) {
		Integer duree = 0;
		if (findByIdWithModules(cursus.getId()).isPresent()) {
			Cursus nouveauCursus = findByIdWithModules(cursus.getId()).get();
			Iterator<Module> iterator = nouveauCursus.getModules().iterator();
			while (iterator.hasNext()) {
				duree += iterator.next().getDuree();
			}
			nouveauCursus.setDuree(duree);
			return nouveauCursus;
		}
		Iterator<Module> iterator = cursus.getModules().iterator();
		while (iterator.hasNext()) {
			duree += iterator.next().getDuree();
		}
		cursus.setDuree(duree);
		return cursus;
	}

}
