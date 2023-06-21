package com.cdm_qatar_2022.demo.controllerstests;

import com.cdm_qatar_2022.demo.entities.Buteur;
import com.cdm_qatar_2022.demo.services.ButeurService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
@Component
@RequestMapping(path = "/api/buteur/controller")
@CrossOrigin(origins = "http://localhost:3000")
public class ButeurController {

  @Autowired
  private ButeurService service;

  @RequestMapping(path = "/scorers", method = RequestMethod.GET)
  public ResponseEntity<List<Buteur>> findAllTheScorers(){
    return service.getAllScorers();
  }

  @RequestMapping(path = "/scorers/report", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> findTheRefereesReport() throws JRException, FileNotFoundException {
    return service.getScorerRecordReport();
  }

  @RequestMapping(path = "/joueur/{id}" , method = RequestMethod.GET)
  public @ResponseBody
  ResponseEntity<Buteur> getButeurById(@PathVariable("id") Long id) {
    return service.getButeurById(id);
  }
}
