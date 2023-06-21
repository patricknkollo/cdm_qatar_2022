package com.cdm_qatar_2022.demo.repositories;

import com.cdm_qatar_2022.demo.entities.Person;
import com.cdm_qatar_2022.demo.projections.PersonFullnameOldProjection;
import com.cdm_qatar_2022.demo.projections.PersonJoueurProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  @Query(value = "SELECT * FROM PERSON",nativeQuery = true)
  public List<PersonFullnameOldProjection> getPersons();

  @Query(value = "SELECT Person.personid, Person.nom, Person.prenom, Person.age, Person.nationaliteid,"
      + "                Joueur.Joueurid, Joueur.dossard "
      + "         FROM PERSON JOIN JOUEUR ON PERSON.PERSONID = JOUEUR.PERSONID",nativeQuery = true)
  public List<PersonJoueurProjection> getPersonJoueurs();
}
