package com.example.vacancychecker.controller;

import com.example.vacancychecker.entity.Vacancy;
import com.example.vacancychecker.service.VacancyService;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public Page<Vacancy> getVacancies(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id,desc") String[] sort) {
        return vacancyService.findSorted(page, size, sort);
    }

    @GetMapping("/location-stats")
    public Map<String, Long> getLocationStats() {
        return vacancyService.findAll()
                .stream()
                .collect(Collectors.groupingBy(Vacancy::getLocation, Collectors.counting()));
    }
}
