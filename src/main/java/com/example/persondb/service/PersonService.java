package com.example.persondb.service;

import com.example.persondb.Model.Category;
import com.example.persondb.Model.News;
import com.example.persondb.repository.CategoryRepository;
import com.example.persondb.repository.NewsRepository;
import com.example.persondb.Model.Person;
import com.example.persondb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, NewsRepository newsRepository, CategoryRepository categoryRepository) {
        this.personRepository = personRepository;
        this.newsRepository = newsRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addPersonWithNews(PersonWithNewsDto personWithNewsDto) {
        Category category = personWithNewsDto.getCategory();
        Category parentCategory = personWithNewsDto.getParentCategory();

        if (parentCategory != null) {
            categoryRepository.save(parentCategory);
        }

        category.setParentCategory(parentCategory);

        categoryRepository.save(category);

        Person person = personWithNewsDto.getPerson();
        personRepository.save(person);
        List<News> newsList = personWithNewsDto.getNewsList();

        for (News news : newsList) {
            news.setCategory(category);
            news.setPerson(person);
            newsRepository.save(news);
        }
    }

    public Person getPersonById(int personId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        return optionalPerson.orElse(null);
    }

    public Person savePerson(Person person) {
       return personRepository.save(person);

    }
}

