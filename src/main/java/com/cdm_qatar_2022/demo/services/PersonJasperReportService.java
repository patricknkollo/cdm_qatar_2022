package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Person;
import com.cdm_qatar_2022.demo.repositories.PersonRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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

@Service
@Component
public class PersonJasperReportService {

  @Autowired
  private PersonRepository repository;

  public ResponseEntity<byte[]> getPersonReport() throws FileNotFoundException, JRException {
    List<Person>persons = repository.findAll(Sort.by(Sort.Direction.ASC, "personid"));
    Map<String, Object> empParams = new HashMap<>();
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(persons);

    JasperPrint empReport =
        JasperFillManager.fillReport
            (
                JasperCompileManager.compileReport(
                    ResourceUtils.getFile("classpath:jasperreports/personreport.jrxml")
                        .getAbsolutePath()) // path of the jasper report
                , empParams // dynamic parameters
                , dataSource
            );
    HttpHeaders headers = new HttpHeaders();
    //set the PDF format
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("filename", "persons_report.pdf");
    //create the report in PDF format
    return new ResponseEntity<byte[]>
        (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
  }

}
