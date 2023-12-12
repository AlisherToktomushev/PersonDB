package com.example.persondb.repository;

import com.example.persondb.Model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Integer> {

}
