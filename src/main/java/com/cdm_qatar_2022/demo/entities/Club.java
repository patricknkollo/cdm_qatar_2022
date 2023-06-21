package com.cdm_qatar_2022.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Club {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long clubid;
  private String nom;
  private String ville;
  private String pays;
  private Long paysid;
}
