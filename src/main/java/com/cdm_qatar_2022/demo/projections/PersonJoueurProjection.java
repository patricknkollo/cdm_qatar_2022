package com.cdm_qatar_2022.demo.projections;

import org.springframework.beans.factory.annotation.Value;

public interface PersonJoueurProjection {

  @Value("#{target.nom}")
  public String getNom1();

  @Value("#{target.prenom}")
  public String getPrenom1();

  @Value("#{target.dossard}")
  public int getDossard1();

  @Value("#{target.age}")
  public int getAge1();

  @Value("#{target.nationaliteid}")
  public String getNationaliteid1();

}
