package com.cdm_qatar_2022.demo.controllerstests;

import com.cdm_qatar_2022.demo.entities.Person;
import com.cdm_qatar_2022.demo.projections.PersonFullnameOldProjection;
import com.cdm_qatar_2022.demo.projections.PersonJoueurProjection;
import com.cdm_qatar_2022.demo.projections.PersonPays;
import com.cdm_qatar_2022.demo.services.PersonJasperReportService;
import com.cdm_qatar_2022.demo.services.PersonService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RequestMapping(path = "/api/person/controller")
@Component
@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class PersonController {

  @Autowired
  private PersonService service;

  @Autowired
  private PersonJasperReportService reportService;

  @RequestMapping(path = "/persons", method = RequestMethod.GET)
  public ResponseEntity<List<Person>> findAllThePersons() {
    return service.getAllPersons();
  }

  @RequestMapping(path = "/save/person", method = RequestMethod.POST)
  public @ResponseBody
  Person savePersonData(@RequestBody Person person) {
    return service.savePerson(person);
  }

  @RequestMapping(path = "/update/person", method = RequestMethod.PUT)
  public @ResponseBody
  Person updatePersonData(@RequestParam("ID") Long id, @RequestBody Person newPerson) {
    return service.updatePerson(id, newPerson);
  }

  @RequestMapping(path = "/person/report", produces = MediaType.APPLICATION_PDF_VALUE)
  public @ResponseBody
  ResponseEntity<byte[]> getPersonsReportData() throws JRException, FileNotFoundException {
    return reportService.getPersonReport();
  }

  @RequestMapping(path = "/projection/person/name", method = RequestMethod.GET)
  public @ResponseBody
  ResponseEntity<List<PersonFullnameOldProjection>> getPersonData() {
    return service.getPersonsData();
  }

  @RequestMapping(path = "/projection/person/joueur", method = RequestMethod.GET)
  public @ResponseBody
  ResponseEntity<List<PersonJoueurProjection>> getPersonJoueurData() {
    return service.getPersonJoueurData();
  }

  @RequestMapping(path = "/person/{personid}" , method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<Person> getPersonById(@PathVariable("personid") Long id) {
    return service.getPersonById(id);
  }

  @RequestMapping(path = "/person/pays/{personid}" , method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<PersonPays> getPersonPaysById(@PathVariable("personid") Long id) {
    return service.getPersonByIdAndCountry(id);
  }
}