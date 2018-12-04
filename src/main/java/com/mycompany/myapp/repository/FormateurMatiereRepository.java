package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FormateurMatiere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormateurMatiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormateurMatiereRepository extends JpaRepository<FormateurMatiere, Long> {

}
