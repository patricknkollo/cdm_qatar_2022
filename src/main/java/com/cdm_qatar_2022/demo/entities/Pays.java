package com.cdm_qatar_2022.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pays {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paysid;
  private String nom;
  private int habitants;
  private int cdms;
  private int superficie;
}
