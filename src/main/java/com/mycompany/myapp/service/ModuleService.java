package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Module;
import com.mycompany.myapp.repository.ModuleRepository;

@Service
public class ModuleService {

	private final ModuleRepository moduleRepository;

	public ModuleService(ModuleRepository moduleRepository) {
		this.moduleRepository = moduleRepository;
	}

	public Module save(Module module) {
		return moduleRepository.save(module);
	}

	public Module saveAndFlush(Module module) {
		return moduleRepository.saveAndFlush(module);
	}

	public List<Module> findAll() {
		return moduleRepository.findAll();
	}

	public Optional<Module> findById(Long id) {
		return moduleRepository.findById(id);
	}

	public void deleteById(Long id) {
		moduleRepository.deleteById(id);
	}
}