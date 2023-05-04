package com.example.vacancychecker.entity;

import lombok.Data;

@Data
public class Meta {
    private int current_page;
    private int from;
    private String path;
    private int per_page;
    private int to;
    private String terms;
    private String info;
}
