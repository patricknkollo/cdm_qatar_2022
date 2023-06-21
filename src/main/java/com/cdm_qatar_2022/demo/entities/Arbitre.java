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
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class Arbitre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long arbitreid;
  private String nom;
  private String prenom;
  private Long personid;
}
