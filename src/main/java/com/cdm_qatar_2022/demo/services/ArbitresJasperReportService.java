package com.cdm_qatar_2022.demo.services;

import com.cdm_qatar_2022.demo.entities.Arbitre;
import com.cdm_qatar_2022.demo.repositories.ArbitreRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
public class ArbitresJasperReportService {

  @Autowired
  private ArbitreRepository repository;

  public ResponseEntity<byte[]> getEmployeeRecordReport() throws FileNotFoundException, JRException {

     //list
    List<Arbitre> arbitres = repository.findAll();
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(arbitres);


      //dynamic parameters required for report
      Map<String, Object> empParams = new HashMap<>();

      JasperPrint empReport =
          JasperFillManager.fillReport
              (
                  JasperCompileManager.compileReport(
                      ResourceUtils.getFile("classpath:jasperreports/arbitresreport.jrxml")
                          .getAbsolutePath()) // path of the jasper report
                  , empParams // dynamic parameters
                  ,dataSource
              );

      HttpHeaders headers = new HttpHeaders();
      //set the PDF format
      headers.setContentType(MediaType.APPLICATION_PDF);
      headers.setContentDispositionFormData("filename", "referees.pdf");
      //create the report in PDF format
      return new ResponseEntity<byte[]>
          (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

  }

  public String exportReport(String format) throws FileNotFoundException, JRException {
    String path = "C:\\Users\\ndedi.patrick.nkollo\\Downloads";
    List<Arbitre> arbitres = repository.findAll();
    //load file and compile it
    File file = ResourceUtils.getFile("classpath:jasperreports/arbitresreport.jrxml");
    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(arbitres);
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("createdby", "me");

    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    if (format.equalsIgnoreCase("html")) {
      JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\referees.html");
    }
    if (format.equalsIgnoreCase("pdf")) {
      JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\referees.pdf");
    }
    if (format.equalsIgnoreCase("xls")) {
      JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
      JRXlsxExporter exporter = new JRXlsxExporter();
      exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
      exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "employees.pdf");
      exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);

      exporter.exportReport();
    }
    return "report generated in path : " + path;
    //return  "report generated in Download";
  }
}
