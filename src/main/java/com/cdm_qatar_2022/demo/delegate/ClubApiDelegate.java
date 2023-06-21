package com.cdm_qatar_2022.demo.delegate;

import com.cdm_qatar_2022.demo.api.ClubApi;
import com.cdm_qatar_2022.demo.entities.Club;
import com.cdm_qatar_2022.demo.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClubApiDelegate implements ClubApi {

  @Autowired
  private ClubService service;

  @Override
  public ResponseEntity<List<Club>> getAllClubsFromDatabase() {
    return service.getAllClubs();
  }
}
