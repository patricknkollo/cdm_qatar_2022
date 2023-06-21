package com.cdm_qatar_2022.demo.controllerstests;

import com.cdm_qatar_2022.demo.entities.Pays;
import com.cdm_qatar_2022.demo.services.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@Controller
@RequestMapping(path = "/api/countries/controller")
@CrossOrigin(origins = "http://localhost:3000")
public class PaysController {

  @Autowired
  private PaysService service;

  @RequestMapping(path = "/countries", method = RequestMethod.GET)
  public ResponseEntity<List<Pays>> findAllTheCountries(){
    return service.getAllCountries();
  }

  @RequestMapping(path = "/country/{id}", method = RequestMethod.GET)
  public ResponseEntity<Pays> findTheCountryWithId(@PathVariable("id") Long paysid){
    ResponseEntity<Pays> pays= service.getCountryById(paysid);
    return pays;
  }
}
