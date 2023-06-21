package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Match;
import com.cdm_qatar_2022.demo.repositories.MatchRepository;
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
public class MatchService {

  private Logger logger = LoggerFactory.getLogger(PaysService.class);

  @Autowired
  private MatchRepository repository;

  public ResponseEntity<List<Match>> getAllGames() {
    List<Match> list = repository.findAll();
    ResponseEntity<List<Match>> result;
    if (!list.isEmpty()) {
      result = new ResponseEntity<>(list, HttpStatus.OK);
    }
    else{
      result = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
      logger.error("the games list is empty!!!");
    }
    return result;
  }

  public Match saveGame(Match match) {
    return repository.save(match);
  }

  public Match updateGame(Long id, Match newGame) {
    Optional<Match> optional = repository.findById(id);
    if (optional.isPresent()) {
      optional.get().setPays1id(newGame.getPays1id());
      optional.get().setPays2id(newGame.getPays2id());
      optional.get().setNom1(newGame.getNom1());
      optional.get().setNom2(newGame.getNom2());
      optional.get().setScorepays1(newGame.getScorepays1());
      optional.get().setScorepays2(newGame.getScorepays2());
      optional.get().setLieu(newGame.getLieu());
      optional.get().setDate(newGame.getDate());
      optional.get().setArbitreid(newGame.getArbitreid());

      return repository.save(optional.get());
    }
    logger.debug("there is no game in the database with the given id!");
    return null;
  }

  public ResponseEntity<Match> getGameById(Long matchid){
    Optional<Match>optional= repository.findById(matchid);
    ResponseEntity<Match>responseEntity;
    if(optional.isPresent()){
      responseEntity= new ResponseEntity<>(optional.get(), HttpStatus.OK);
      logger.info("the Match with the id "+ matchid+" exist");
      return responseEntity;
    }
    logger.info("the match with the id "+ matchid+" doesn't exist!!! ");
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<byte[]> getGameReport() throws FileNotFoundException, JRException {
    List<Match>matchsList = repository.findAll(Sort.by(Sort.Direction.ASC, "matchid"));
    Map<String, Object> empParams = new HashMap<>();
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(matchsList);

    JasperPrint empReport =
        JasperFillManager.fillReport
            (
                JasperCompileManager.compileReport(
                    ResourceUtils.getFile("classpath:jasperreports/matchs_report.jrxml")
                        .getAbsolutePath()) // path of the jasper report
                , empParams // dynamic parameters
                , dataSource
            );
    HttpHeaders headers = new HttpHeaders();
    //set the PDF format
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("filename", "matchs_report.pdf");
    //create the report in PDF format
    return new ResponseEntity<byte[]>
        (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
  }
}
