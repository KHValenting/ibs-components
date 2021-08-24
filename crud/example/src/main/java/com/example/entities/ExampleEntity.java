package com.example.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "EXAMPLE", schema = "EXAMPLE")
public class ExampleEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String exampleFieldOne;

    private Long exampleFieldTwo;
}
