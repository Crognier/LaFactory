package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Formateur;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Formateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormateurRepository extends JpaRepository<Formateur, Long> {
	Optional<Formateur> findByAccountId(@Param("id") Long id);
}
