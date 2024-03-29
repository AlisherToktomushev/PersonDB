package com.example.persondb.Controller;
import com.example.persondb.Model.News;
import com.example.persondb.repository.CategoryRepository;
import com.example.persondb.repository.NewsRepository;
import com.example.persondb.Model.Person;
import com.example.persondb.repository.PersonRepository;
import com.example.persondb.service.PersonService;
import com.example.persondb.service.PersonWithNewsDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(PersonController.class);
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
    @GetMapping("/getNews")
    public ResponseEntity<Iterable<News>> getAllNews() {
        try {
            logger.info("вывод новостей");
            Iterable<News> allNews = newsRepository.findAll();
            return new ResponseEntity<>(allNews, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("ошибка при вывода новостей", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/personAll")
    public ResponseEntity<Iterable<Person>>getAllPerson(){
        try {
            logger.info("вывод всех людей");
            Iterable<Person> allPerson = personRepository.findAll();
            return new ResponseEntity<>(allPerson, HttpStatus.OK);
        }catch (Exception e){
            logger.error("ошибка при выводе людей", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PutMapping("/updatePerson{personId}")
    public ResponseEntity<Person> update2Person(@PathVariable int personId, @RequestBody Person updatedPerson) {
        logger.info("Обновление человека {}", personId);
        Person person = personService.getPersonById(personId);
        if (person == null)
          throw new IllegalArgumentException("не найден");

        person.setName(updatedPerson.getName());
        person.setAge(updatedPerson.getAge());
        person.setJob(updatedPerson.getJob());

        Person updated = personService.savePerson(person);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPersonWithNews(@RequestBody PersonWithNewsDto personWithNewsDto) {
           if (personWithNewsDto!=null) {
               logger.info("добавление человека");
               personService.addPersonWithNews(personWithNewsDto);
               return ResponseEntity.ok("Added successfully");
           }else
            logger.error("ошибка при ввода человека", personWithNewsDto);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ошибка");

    }
    @GetMapping("/personId{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        try {
            logger.info("Fetching person with ID {}", id);
            Person person = personRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Person не найден по ID: " + id));

            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Person не найден {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deletePerson{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable int id) {
        logger.info("удаление человека {}", id);
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
