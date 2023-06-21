package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Joueur;
import com.cdm_qatar_2022.demo.repositories.JoueurRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Service
public class JoueurService {

  private Logger logger = LoggerFactory.getLogger(PaysService.class);

  @Autowired
  private JoueurRepository repository;

  public ResponseEntity<List<Joueur>> getAllPlayers() {
    List<Joueur> list = repository.findAll();
    ResponseEntity<List<Joueur>> result;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    }
    else{
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the players list is empty!!!");
    }
    return result;
  }

  public Joueur saveJoueur(Joueur joueur) {
    return repository.save(joueur);
  }

  public Joueur updateJoueur(Long id, Joueur newJoueur) {
    Optional<Joueur> optional = repository.findById(id);
    if (optional.isPresent()) {
      optional.get().setPrenom(newJoueur.getPrenom());
      optional.get().setNom(newJoueur.getNom());
      optional.get().setPersonid(newJoueur.getPersonid());
      optional.get().setDossard(newJoueur.getDossard());


      return repository.save(optional.get());
    }
    logger.debug("there is no player in the database with the given id!");
    return null;
  }

  public ResponseEntity<Joueur> getJoueurById(Long joueurid){
    Optional<Joueur>optional= repository.findById(joueurid);
    ResponseEntity<Joueur>responseEntity;
    if(optional.isPresent()){
      responseEntity= new ResponseEntity<>(optional.get(), HttpStatus.OK);
      logger.info("the player with the id "+ joueurid+" exist and his/her name is "
          +optional.get().getPrenom()+" "+optional.get().getNom());
      return responseEntity;
    }
    logger.info("the player with the id "+ joueurid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<byte[]> getJoueurReport() throws FileNotFoundException, JRException {
    List<Joueur>persons = repository.findAll(Sort.by(Sort.Direction.ASC, "joueurid"));
    Map<String, Object> empParams = new HashMap<>();
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(persons);

    JasperPrint empReport =
        JasperFillManager.fillReport
            (
                JasperCompileManager.compileReport(
                    ResourceUtils.getFile("classpath:jasperreports/joueursreport.jrxml")
                        .getAbsolutePath()) // path of the jasper report
                , empParams // dynamic parameters
                , dataSource
            );
    HttpHeaders headers = new HttpHeaders();
    //set the PDF format
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("filename", "joueur_report.pdf");
    //create the report in PDF format
    return new ResponseEntity<byte[]>
        (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
  }
}
