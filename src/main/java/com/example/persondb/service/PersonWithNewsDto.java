package com.example.persondb.service;

import com.example.persondb.Model.Category;
import com.example.persondb.Model.News;
import com.example.persondb.Model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PersonWithNewsDto {
    private Person person;
    private Category category;
    private List<News> newsList;
    private Category parentCategory;

}