package com.example.vacancychecker.repository;

import com.example.vacancychecker.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    boolean existsVacancyByUrl(String url);
}
