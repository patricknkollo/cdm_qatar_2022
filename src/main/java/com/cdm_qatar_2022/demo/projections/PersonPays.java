package com.cdm_qatar_2022.demo.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonPays {

  private Long personid;
  private String nom;
  private String prenom;
  private int age;
  private Long nationaliteid;
  private Long paysid;
  private String nompays;
  private int habitants;
  private int cdms;
  private int superficie;
}
