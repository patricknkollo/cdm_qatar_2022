package com.cdm_qatar_2022.demo.repositories;

import com.cdm_qatar_2022.demo.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface ClubRepository extends JpaRepository<Club, Long> {
}
