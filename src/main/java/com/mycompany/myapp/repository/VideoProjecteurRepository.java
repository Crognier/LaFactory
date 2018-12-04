package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VideoProjecteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VideoProjecteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoProjecteurRepository extends JpaRepository<VideoProjecteur, Long> {

}
