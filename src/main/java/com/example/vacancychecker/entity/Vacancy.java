package com.example.vacancychecker.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    @Column(name = "company_name")
    private String companyName;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean remote;
    private String url;
    @ElementCollection
    @Column(name = "tag")
    private List<String> tags;
    @ElementCollection
    @CollectionTable(name = "job_types")
    @Column(name = "job_type")
    private List<String> jobTypes;
    private String location;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Long createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacancy)) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(getUrl(), vacancy.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl());
    }

    @Override
    public String toString() {
        return "Vacancy{"
                + "id=" + id
                + ", slug='" + slug + '\''
                + ", companyName='" + companyName + '\''
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", remote=" + remote
                + ", url='" + url + '\''
                + ", tags=" + tags
                + ", jobTypes=" + jobTypes
                + ", location='" + location + '\''
                + ", createdAt=" + createdAt
                + '}';
    }
}
