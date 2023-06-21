package com.cdm_qatar_2022.demo.repositories;

import com.cdm_qatar_2022.demo.entities.Arbitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface ArbitreRepository extends JpaRepository<Arbitre, Long> {
}
