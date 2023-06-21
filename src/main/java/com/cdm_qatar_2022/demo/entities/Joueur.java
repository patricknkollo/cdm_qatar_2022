package com.cdm_qatar_2022.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Joueur {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long joueurid;
  private String nom;
  private String prenom;
  private int dossard;
  private Long personid;
}
