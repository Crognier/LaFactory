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
		System.out.println("-------------------");
		System.out.println("\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n");
		System.out.println("save repo");
		System.out.println("-------------------");
		System.out.println(formateur.toString());
		System.out.println();
		return formateurRepository.save(formateur);
	}

	public Formateur saveAndFlush(Formateur formateur) {
		System.out.println("-------------------");
		System.out.println("\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n");
		System.out.println("save and flunch repo");
		System.out.println("-------------------");
		System.out.println(formateur.toString());
		System.out.println();
		return formateurRepository.saveAndFlush(formateur);
	}

	public List<Formateur> findAll() {
		System.out.println("-------------------");
		System.out.println("\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n");
		System.out.println("find all repo");
		for (Formateur formateur: formateurRepository.findAll()) {
			System.out.println(formateur.toString());
		}
		System.out.println("-------------------");
		return formateurRepository.findAll();
	}

	public Optional<Formateur> findById(Long id) {
		System.out.println("-------------------");
		System.out.println("\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n");
		System.out.println("find by id repo");
		System.out.println("-------------------");
		System.out.println(formateurRepository.findById(id).get().toString());
		System.out.println();
		return formateurRepository.findById(id);
	}

	public void deleteById(Long id) {
		System.out.println("-------------------");
		System.out.println("\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n");
		System.out.println("delete by id repo");
		System.out.println("-------------------");
		System.out.println(formateurRepository.findById(id).get().toString());
		System.out.println();
		formateurRepository.deleteById(id);
	}
}
