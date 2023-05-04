package com.example.vacancychecker.service;

import com.example.vacancychecker.entity.Link;
import com.example.vacancychecker.entity.Meta;
import com.example.vacancychecker.entity.Vacancy;
import com.example.vacancychecker.repository.VacancyRepository;
import com.example.vacancychecker.service.dto.VacancyDto;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class VacancyLoader {
    public static final String URL = "https://www.arbeitnow.com/api/job-board-api";
    private static final int HOUR = 60 * 60 * 1000;
    private final RestTemplate restTemplate = new RestTemplate();
    private final VacancyRepository repository;

    @Scheduled(fixedRate = HOUR)
    public void loadJobs() {
        for (int page = 1; page <= 5; page++) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                    .queryParam("page", page);
            String url = builder.toUriString();

            ResponseEntity<MyResponse> response = restTemplate.exchange(url, HttpMethod.GET,
                    null, MyResponse.class);
            VacancyDto[] responseData = response.getBody().getData();

            if (responseData != null) {
                Arrays.stream(responseData)
                        .map(this::toVacancy)
                        .filter(vacancy -> !repository.existsVacancyByUrl(vacancy.getUrl()))
                        .forEach(repository::save);
            }
        }
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

