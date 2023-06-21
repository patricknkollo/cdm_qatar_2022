package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Pays;
import com.cdm_qatar_2022.demo.entities.Person;
import com.cdm_qatar_2022.demo.projections.PersonFullnameOldProjection;
import com.cdm_qatar_2022.demo.projections.PersonJoueurProjection;
import com.cdm_qatar_2022.demo.projections.PersonPays;
import com.cdm_qatar_2022.demo.repositories.PaysRepository;
import com.cdm_qatar_2022.demo.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class PersonService {

  private Logger logger = LoggerFactory.getLogger(PaysService.class);

  @Autowired
  private PersonRepository repository;

  @Autowired
  private PaysRepository paysRepository;

  public ResponseEntity<List<Person>> getAllPersons() {
    List<Person> list = repository.findAll();
    ResponseEntity<List<Person>> result;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    } else {
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the persons list is empty!!!");
    }
    return result;
  }

  public Person savePerson(Person person) {
    return repository.save(person);
  }

  public Person updatePerson(Long id, Person newPerson) {
    Optional<Person> personOptional = repository.findById(id);
    if (personOptional.isPresent()) {
      personOptional.get().setPrenom(newPerson.getPrenom());
      personOptional.get().setNom(newPerson.getNom());
      personOptional.get().setAge(newPerson.getAge());
      personOptional.get().setNationaliteid(newPerson.getNationaliteid());

      return repository.save(personOptional.get());
    }
    logger.debug("there is no person in the database with the given id!");
    return null;
  }

  public ResponseEntity<List<PersonFullnameOldProjection>> getPersonsData() {
    List<PersonFullnameOldProjection> list = repository.getPersons();
    ResponseEntity<List<PersonFullnameOldProjection>> result ;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    } else {
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the persons list is empty!!!");
    }
    return result;
  }

  public ResponseEntity<List<PersonJoueurProjection>> getPersonJoueurData() {
    List<PersonJoueurProjection> list = repository.getPersonJoueurs();
    ResponseEntity<List<PersonJoueurProjection>> result;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    } else {
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the persons list is empty!!!");
    }
    return result;
  }

  public ResponseEntity<Person> getPersonById(Long personid){
    Optional<Person>optionalPerson= repository.findById(personid);
    ResponseEntity<Person>responseEntity;
    if(optionalPerson.isPresent()){
      responseEntity= new ResponseEntity<>(optionalPerson.get(), HttpStatus.OK);
      logger.info("the person with the id "+ personid+" exist and his/her name is "
          +optionalPerson.get().getPrenom()+" "+optionalPerson.get().getNom());
      return responseEntity;
    }
    logger.info("the person with the id "+ personid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<PersonPays> getPersonByIdAndCountry(Long personid){
    Optional<Person>optionalPerson= repository.findById(personid);
    ResponseEntity<PersonPays>responseEntity;
    if(optionalPerson.isPresent()){
      Long nationaliteid = optionalPerson.get().getNationaliteid();
      Pays paysOptional = paysRepository.getById(nationaliteid);
      PersonPays resultPersonPays = getPersonPaysMapping(paysOptional, optionalPerson.get());
      responseEntity= new ResponseEntity<>(resultPersonPays, HttpStatus.OK);
      logger.info("the person with the id "+ personid+" exist and his/her name is "
          +optionalPerson.get().getPrenom()+" "+optionalPerson.get().getNom());
      return responseEntity;
    }
    logger.info("the person with the id "+ personid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  public PersonPays getPersonPaysMapping(Pays pays, Person person){
    PersonPays personPays = new PersonPays();
    personPays.setPersonid(person.getPersonid());
    personPays.setAge(person.getAge());
    personPays.setNom(person.getNom());
    personPays.setPrenom(person.getPrenom());
    personPays.setPaysid(pays.getPaysid());
    personPays.setNationaliteid(person.getNationaliteid());
    personPays.setCdms(pays.getCdms());
    personPays.setHabitants(pays.getHabitants());
    personPays.setNompays(pays.getNom());

    return personPays;
  }

}
