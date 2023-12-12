package com.example.persondb.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int age;
    private String job;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<News> personNewsList = new ArrayList<>();

}
