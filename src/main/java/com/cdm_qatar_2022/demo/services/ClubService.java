package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Club;
import com.cdm_qatar_2022.demo.repositories.ClubRepository;
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
public class ClubService {

  private Logger logger = LoggerFactory.getLogger(PaysService.class);

  @Autowired
  private ClubRepository repository;

  public ResponseEntity<List<Club>> getAllClubs() {
    List<Club> list = repository.findAll();
    ResponseEntity<List<Club>> result;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    }
    else{
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the Clubs list is empty!!!");
    }
    return result;
  }

  public Club saveClub(Club club) {
    return repository.save(club);
  }

  public Club updateClub(Long id, Club newClub) {
    Optional<Club> optional = repository.findById(id);
    if (optional.isPresent()) {
      optional.get().setPays(newClub.getPays());
      optional.get().setNom(newClub.getNom());
      optional.get().setVille(newClub.getVille());
      optional.get().setPaysid(newClub.getPaysid());

      return repository.save(optional.get());
    }
    logger.debug("there is no club in the database with the given id!");
    return null;
  }

  public ResponseEntity<Club> getClubById(Long clubid){
    Optional<Club>optional= repository.findById(clubid);
    ResponseEntity<Club>responseEntity;
    if(optional.isPresent()){
      responseEntity= new ResponseEntity<>(optional.get(), HttpStatus.OK);
      logger.info("the club with the id "+ clubid+" exist and his/her name is "
          +optional.get().getNom()+" "+optional.get().getNom());
      return responseEntity;
    }
    logger.info("the club with the id "+ clubid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
}
