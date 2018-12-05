package com.mycompany.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Cursus;
import com.mycompany.myapp.domain.VideoProjecteur;
import com.mycompany.myapp.repository.CursusRepository;
import com.mycompany.myapp.repository.VideoProjecteurRepository;


@Service
public class VideoProjecteurService {
	
	private final VideoProjecteurRepository videoprojecteurRepository;
	
	public VideoProjecteurService(VideoProjecteurRepository videoprojecteurRepository) {
		this.videoprojecteurRepository = videoprojecteurRepository;
	}
	
	public VideoProjecteur save(VideoProjecteur videoProjecteur) {
		return videoprojecteurRepository.save(videoProjecteur);
	}
	
	public VideoProjecteur saveAndFlush(VideoProjecteur videoProjecteur) {
		return videoprojecteurRepository.saveAndFlush(videoProjecteur);
	}
	
	public List<VideoProjecteur> findAll() {
		return videoprojecteurRepository.findAll();
	}
	
	public Optional<VideoProjecteur> findById(Long id) {
		return videoprojecteurRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		videoprojecteurRepository.deleteById(id);
	}

}
