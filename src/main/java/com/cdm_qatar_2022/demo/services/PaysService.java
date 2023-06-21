package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Pays;
import com.cdm_qatar_2022.demo.repositories.PaysRepository;
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
public class PaysService {

  private Logger logger = LoggerFactory.getLogger(PaysService.class);

  @Autowired
  private PaysRepository repository;

  public ResponseEntity<List<Pays>> getAllCountries() {
    List<Pays> paysList = repository.findAll();
    ResponseEntity<List<Pays>> result ;
    if (!paysList.isEmpty()) {
      result = new ResponseEntity<>(paysList, HttpStatus.OK);
    }
    else{
      result = new ResponseEntity<>(paysList, HttpStatus.NOT_FOUND);
      logger.error("the countries list is empty!!!");
    }
    return result;
  }

  public Pays saveCountry(Pays pays) {
    return repository.save(pays);
  }

  public Pays updateCountry(Long id, Pays newCountry) {
    Optional<Pays> optional = repository.findById(id);
    if (optional.isPresent()) {
      optional.get().setPaysid(newCountry.getPaysid());
      optional.get().setNom(newCountry.getNom());
      optional.get().setHabitants(newCountry.getHabitants());
      optional.get().setCdms(newCountry.getCdms());
      optional.get().setSuperficie(newCountry.getSuperficie());

      return repository.save(optional.get());
    }
    logger.debug("there is no game in the database with the given id!");
    return null;
  }

  public ResponseEntity<Pays> getCountryById(Long countryid){
    Optional<Pays>optional= repository.findById(countryid);
    ResponseEntity<Pays>responseEntity;
    if(optional.isPresent()){
      responseEntity= new ResponseEntity<>(optional.get(), HttpStatus.OK);
      logger.info("the Country with the id "+ countryid+" exist");
      return responseEntity;
    }
    logger.info("the country with the id "+ countryid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
}
