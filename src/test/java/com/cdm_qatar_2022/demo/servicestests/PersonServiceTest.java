package com.cdm_qatar_2022.demo.servicestests;

import com.cdm_qatar_2022.demo.entities.Person;
import com.cdm_qatar_2022.demo.projections.PersonFullnameOldProjection;
import com.cdm_qatar_2022.demo.repositories.PersonRepository;
import com.cdm_qatar_2022.demo.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class PersonServiceTest {

  @InjectMocks
  private PersonService personService;

  @Mock
  private PersonRepository repository;

  private Person newPerson = new Person(1L, "nkollo", "patrick", 33, 7L);

  private PersonFullnameOldProjection projection = new PersonFullnameOldProjection() {
    @Override
    public String getNom() {
      return "enangue";
    }

    @Override
    public String getPrenom() {
      return "rosine";
    }

    @Override
    public int getAge() {
      return 96;
    }
  };

  @Test
  void test_getAllPersons(){

    List<Person> list = Arrays.asList();
    ResponseEntity<List<Person>> responseEntity = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    Mockito.when(repository.findAll()).thenReturn(list);
    ResponseEntity<List<Person>> result = personService.getAllPersons();
    Assertions.assertEquals(responseEntity, result);

  }

  @Test
  void test_UpdatePerson(){

    Optional<Person> personOptional = Optional.of(newPerson);
    Mockito.when(repository.findById(6L)).thenReturn(personOptional);
    Mockito.when(repository.save(personOptional.get())).thenReturn(newPerson);
    Person result = personService.updatePerson(6L, newPerson);
    Assertions.assertEquals(newPerson.getAge(), result.getAge());

  }

  @Test
  void test_getPersonsData(){

    Mockito.when(repository.getPersons()).thenReturn(Arrays.asList(projection));
    ResponseEntity<List<PersonFullnameOldProjection>> result = personService.getPersonsData();
    Assertions.assertEquals(result.getBody(), (Arrays.asList(projection)));
  }
}
