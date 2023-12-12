package com.example.persondb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class PersonDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonDbApplication.class, args);
    }

}

