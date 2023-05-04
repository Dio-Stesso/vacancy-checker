package com.example.vacancychecker.service.dto;

import lombok.Data;

@Data
public class VacancyDto {
    private String slug;
    private String company_name;
    private String title;
    private String description;
    private boolean remote;
    private String url;
    private String[] tags;
    private String[] job_types;
    private String location;
    private Long created_at;
}
