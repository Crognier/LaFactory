package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cursus;
import com.mycompany.myapp.domain.Module;

import io.swagger.models.Model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cursus entity.
 */
@Repository
public interface CursusRepository extends JpaRepository<Cursus, Long> {

	
	@Query("select c from Cursus c left join fetch c.modules where c.id=:id")
	Optional<Cursus> findByIdWithModules(@Param("id") Long id);
	
	@Query("select distinct c from Cursus c left join fetch c.modules")
	List<Cursus> findAllWithModules();
	
	
}
