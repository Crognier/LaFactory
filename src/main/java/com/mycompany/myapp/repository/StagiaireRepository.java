package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Stagiaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Stagiaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StagiaireRepository extends JpaRepository<Stagiaire, Long> {

}
