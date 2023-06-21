package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Buteur;
import com.cdm_qatar_2022.demo.repositories.ButeurRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Component
public class ButeurService {

  private Logger logger = LoggerFactory.getLogger(PaysService.class);

  @Autowired
  private ButeurRepository repository;

  public ResponseEntity<List<Buteur>> getAllScorers() {
    List<Buteur> list = repository.findAll();
    ResponseEntity<List<Buteur>> result;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    }
    else{
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the scorers list is empty!!!");
    }
    return result;
  }

  public ResponseEntity<byte[]> getScorerRecordReport() throws FileNotFoundException, JRException {

    //list
    List<Buteur> buteurs = repository.findAll();
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(buteurs);


    //dynamic parameters required for report
    Map<String, Object> empParams = new HashMap<>();

    JasperPrint empReport =
        JasperFillManager.fillReport
            (
                JasperCompileManager.compileReport(
                    ResourceUtils.getFile("classpath:jasperreports/buteursreport.jrxml")
                        .getAbsolutePath()) // path of the jasper report
                , empParams // dynamic parameters
                ,dataSource
            );

    HttpHeaders headers = new HttpHeaders();
    //set the PDF format
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("filename", "buteurs.pdf");
    //create the report in PDF format
    return new ResponseEntity<byte[]>
        (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

  }

  public Buteur saveButeur(Buteur buteur) {
    return repository.save(buteur);
  }

  public Buteur updatePerson(Long id, Buteur newButeur) {
    Optional<Buteur> optional = repository.findById(id);
    if (optional.isPresent()) {
      optional.get().setPrenom(newButeur.getPrenom());
      optional.get().setNom(newButeur.getNom());
      optional.get().setJoueurid(newButeur.getJoueurid());
      optional.get().setButs(newButeur.getButs());


      return repository.save(optional.get());
    }
    logger.debug("there is no person in the database with the given id!");
    return null;
  }

  public ResponseEntity<Buteur> getButeurById(Long buteurid){
    Optional<Buteur>optional= repository.findById(buteurid);
    ResponseEntity<Buteur>responseEntity;
    if(optional.isPresent()){
      responseEntity= new ResponseEntity<>(optional.get(), HttpStatus.OK);
      logger.info("the person with the id "+ buteurid+" exist and his/her name is "
          +optional.get().getPrenom()+" "+optional.get().getNom());
      return responseEntity;
    }
    logger.info("the person with the id "+ buteurid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
}
