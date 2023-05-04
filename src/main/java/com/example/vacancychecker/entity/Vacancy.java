package com.example.vacancychecker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String companyName;
    private String title;
    @Column(length = 10000)
    private String description;
    private boolean remote;
    private String url;
    @ElementCollection
    private List<String> tags;
    @ElementCollection
    private List<String> jobTypes;
    private String location;
    private Long createdAt;
}
