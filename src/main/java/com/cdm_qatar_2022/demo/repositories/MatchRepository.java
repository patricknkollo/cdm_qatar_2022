package com.cdm_qatar_2022.demo.repositories;

import com.cdm_qatar_2022.demo.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
