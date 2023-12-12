package com.example.persondb.Controller;
import com.example.persondb.repository.CategoryRepository;
import com.example.persondb.repository.NewsRepository;
import com.example.persondb.Model.Person;
import com.example.persondb.repository.PersonRepository;
import com.example.persondb.service.PersonService;
import com.example.persondb.service.PersonWithNewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/personAll")
    public Iterable<Person> getAllPerson(){
                return personRepository.findAll();
    }
    @PutMapping("/updatePerson{personId}")
    public ResponseEntity<Person> update2Person(@PathVariable int personId, @RequestBody Person updatedPerson) {
        Person person = personService.getPersonById(personId);

        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        person.setName(updatedPerson.getName());
        person.setAge(updatedPerson.getAge());
        person.setJob(updatedPerson.getJob());

        Person updated = personService.savePerson(person);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPersonWithNews(@RequestBody PersonWithNewsDto personWithNewsDto) {
        personService.addPersonWithNews(personWithNewsDto);
        return ResponseEntity.ok("added successfully");
    }
   @GetMapping("/personId{id}")
    public Person getPersonById(@PathVariable int id) {
        return personRepository.findById(id).orElse(null);
    }
//    @PutMapping("/updatePerson{id}")
//    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person updatedPerson) {
//        return personRepository.findById(id)
//                .map(existingPerson -> {
//                    existingPerson.setName(updatedPerson.getName());
//                    existingPerson.setJob(updatedPerson.getJob());
//                    existingPerson.setAge(updatedPerson.getAge());
//                    personRepository.save(existingPerson);
//                    return ResponseEntity.ok(existingPerson);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
    @DeleteMapping("/deletePerson{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable int id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
