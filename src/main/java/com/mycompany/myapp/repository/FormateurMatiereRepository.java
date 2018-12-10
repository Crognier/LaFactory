package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FormateurMatiere;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormateurMatiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormateurMatiereRepository extends JpaRepository<FormateurMatiere, Long> {
	Optional<List<FormateurMatiere>> findByFormateurId(@Param("id") Long id);
}
