package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Indisponibilite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Indisponibilite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndisponibiliteRepository extends JpaRepository<Indisponibilite, Long> {

}
