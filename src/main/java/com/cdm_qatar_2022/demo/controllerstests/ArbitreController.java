package com.cdm_qatar_2022.demo.controllerstests;

import com.cdm_qatar_2022.demo.entities.Arbitre;
import com.cdm_qatar_2022.demo.services.ArbitreService;
import com.cdm_qatar_2022.demo.services.ArbitresJasperReportService;
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
@RequestMapping(path = "/api/referees/controller")
@CrossOrigin(origins = "http://localhost:3000")
public class ArbitreController {

  @Autowired
  private ArbitreService service;

  @Autowired
  private ArbitresJasperReportService reportService;

  @RequestMapping(path = "/referees", method = RequestMethod.GET)
  public ResponseEntity<List<Arbitre>> findAllTheReferees(){
    return service.getAllreferees();
  }

  @RequestMapping(path = "/referees/report", produces = MediaType.APPLICATION_PDF_VALUE)
  public @ResponseBody ResponseEntity<byte[]> findTheRefereesReport() throws JRException, FileNotFoundException {
    return reportService.getEmployeeRecordReport();
  }

  //@RequestMapping(path = "/referees/report/{format}", produces = MediaType.APPLICATION_PDF_VALUE)
  @RequestMapping(path = "/referees/report/{format}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public @ResponseBody String findTheRefereesReportHtml(@PathVariable("format") String format) throws JRException, FileNotFoundException {
    return reportService.exportReport(format);
  }

  @RequestMapping(path = "/referees/{refid}", method = RequestMethod.GET)
  public ResponseEntity<Arbitre> findTheRefereesWithID(@PathVariable("refid") Long id){
    return service.getArbitreById(id);
  }

  @RequestMapping(path = "/save/referees", method = RequestMethod.POST)
  public @ResponseBody Arbitre saveTheReferees(@RequestBody Arbitre arbitre){
    return service.saveArbitre(arbitre);
  }
}
