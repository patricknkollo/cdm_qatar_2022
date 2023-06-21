package com.cdm_qatar_2022.demo.api;

import com.cdm_qatar_2022.demo.entities.Club;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@Component
@RequestMapping(path = "/api/club/controller")
@CrossOrigin(origins = "http://localhost:3000")
public interface ClubApi {

  @RequestMapping(path = "/clubs", method = RequestMethod.GET)
  public ResponseEntity<List<Club>> getAllClubsFromDatabase();
}
