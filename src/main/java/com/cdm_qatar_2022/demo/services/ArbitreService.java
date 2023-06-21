package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Arbitre;
import com.cdm_qatar_2022.demo.entities.Person;
import com.cdm_qatar_2022.demo.repositories.ArbitreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class ArbitreService {

  private Logger logger = LoggerFactory.getLogger(PaysService.class);

  @Autowired
  private ArbitreRepository repository;

  public ResponseEntity<List<Arbitre>> getAllreferees() {
    List<Arbitre> list = repository.findAll();
    ResponseEntity<List<Arbitre>> result;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    }
    else{
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the referees list is empty!!!");
    }
    return result;
  }

  public Arbitre saveArbitre(Arbitre arbitre) {
    return repository.save(arbitre);
  }

  public Arbitre updateArbitre(Long id, Person newArbitre) {
    Optional<Arbitre> optional = repository.findById(id);
    if (optional.isPresent()) {
      optional.get().setPrenom(newArbitre.getPrenom());
      optional.get().setNom(newArbitre.getNom());
      optional.get().setPersonid(newArbitre.getPersonid());


      return repository.save(optional.get());
    }
    logger.debug("there is no person in the database with the given id!");
    return null;
  }

  public ResponseEntity<Arbitre> getArbitreById(Long arbitreid){
    Optional<Arbitre>optional= repository.findById(arbitreid);
    ResponseEntity<Arbitre>responseEntity;
    if(optional.isPresent()){
      responseEntity= new ResponseEntity<>(optional.get(), HttpStatus.OK);
      logger.info("the person with the id "+ arbitreid+" exist and his/her name is "
          +optional.get().getPrenom()+" "+optional.get().getNom());
      return responseEntity;
    }
    logger.info("the person with the id "+ arbitreid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
}
