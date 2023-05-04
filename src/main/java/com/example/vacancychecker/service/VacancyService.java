package com.example.vacancychecker.service;

import com.example.vacancychecker.entity.Vacancy;
import com.example.vacancychecker.repository.VacancyRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;

    public Page<Vacancy> findSorted(int page, int size, String[] sort) {
        return vacancyRepository.findAll(PageRequest.of(page, size, getSort(sort)));
    }

    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    private Sort getSort(String[] sort) {
        if (sort != null && sort.length == 2) {
            String property = sort[0];
            Sort.Direction direction = Sort.Direction.fromString(sort[1]);
            return Sort.by(direction, property);
        } else {
            return Sort.unsorted();
        }
    }
}
