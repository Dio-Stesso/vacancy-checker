package com.example.vacancychecker.service;

import com.example.vacancychecker.entity.Link;
import com.example.vacancychecker.entity.Meta;
import com.example.vacancychecker.entity.Vacancy;
import com.example.vacancychecker.repository.VacancyRepository;
import com.example.vacancychecker.service.dto.VacancyDto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class VacancyLoader {
    public static final String URL = "https://www.arbeitnow.com/api/job-board-api?page={page}";
    private static final int HOUR = 60 * 60 * 1000;
    private final RestTemplate restTemplate = new RestTemplate();
    private final VacancyRepository repository;

    @Scheduled(fixedRate = HOUR)
    public void loadJobs() {
        List<MyResponse> responses = IntStream.rangeClosed(1, 5)
                .parallel()
                .mapToObj(this::fetchVacancies)
                .collect(Collectors.toList());

        List<Vacancy> vacancies = responses.stream()
                .flatMap(response -> Arrays.stream(response.getData()))
                .map(this::toVacancy)
                .filter(vacancy -> !repository.existsVacancyByUrl(vacancy.getUrl()))
                .collect(Collectors.toList());

        if (!vacancies.isEmpty()) {
            repository.saveAll(vacancies);
        }
    }

    @Cacheable(value = "vacancies", key = "{#page}")
    public MyResponse fetchVacancies(int page) {
        String url = URL.replace("{page}", String.valueOf(page));
        ResponseEntity<MyResponse> response = restTemplate.getForEntity(url, MyResponse.class);
        return response.getBody();
    }

    @Data
    static class MyResponse {
        private VacancyDto[] data;
        private Link links;
        private Meta meta;
    }

    private Vacancy toVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(vacancyDto.getTitle());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setLocation(vacancyDto.getLocation());
        vacancy.setCreatedAt(vacancyDto.getCreated_at());
        vacancy.setJobTypes(Arrays.stream(vacancyDto.getJob_types()).toList());
        vacancy.setRemote(vacancyDto.isRemote());
        vacancy.setCompanyName(vacancyDto.getCompany_name());
        vacancy.setSlug(vacancyDto.getSlug());
        vacancy.setUrl(vacancyDto.getUrl());
        vacancy.setTags(Arrays.stream(vacancyDto.getTags()).toList());
        return vacancy;
    }
}

