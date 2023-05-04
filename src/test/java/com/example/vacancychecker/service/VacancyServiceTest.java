package com.example.vacancychecker.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.vacancychecker.entity.Vacancy;
import com.example.vacancychecker.repository.VacancyRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class VacancyServiceTest {

    @Mock
    private VacancyRepository vacancyRepository;

    @InjectMocks
    private VacancyService vacancyService;

    @Test
    public void testFindSortedWithSortByTitleAscending() {
        String[] sort = new String[]{"title", "asc"};
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
        when(vacancyRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Vacancy> vacancies = vacancyService.findSorted(0, 10, sort);
        Assertions.assertNotNull(vacancies);
        verify(vacancyRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindSortedWithSortByTitleDescending() {
        String[] sort = new String[]{"title", "desc"};
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").descending());
        when(vacancyRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Vacancy> vacancies = vacancyService.findSorted(0, 10, sort);
        Assertions.assertNotNull(vacancies);
        verify(vacancyRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindSortedWithInvalidSort() {
        String[] sort = new String[]{"invalid_sort_param"};
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(vacancyRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Vacancy> vacancies = vacancyService.findSorted(0, 10, sort);
        Assertions.assertNotNull(vacancies);
        verify(vacancyRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindSortedWithNullSort() {
        String[] sort = null;
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(vacancyRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Vacancy> vacancies = vacancyService.findSorted(0, 10, sort);
        Assertions.assertNotNull(vacancies);
        verify(vacancyRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindSortedWithEmptySort() {
        String[] sort = new String[]{};
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(vacancyRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<Vacancy> vacancies = vacancyService.findSorted(0, 10, sort);
        Assertions.assertNotNull(vacancies);
        verify(vacancyRepository, times(1)).findAll(pageable);
    }
}
