package com.example.vacancychecker.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.vacancychecker.entity.Vacancy;
import com.example.vacancychecker.service.VacancyService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class VacancyControllerTest {
    @Mock
    private VacancyService vacancyService;

    @InjectMocks
    private VacancyController vacancyController;

    @Test
    public void testGetVacancies() {
        Page<Vacancy> expectedPage = new PageImpl<>(Arrays.asList(new Vacancy(), new Vacancy()));
        when(vacancyService.findSorted(anyInt(), anyInt(), any())).thenReturn(expectedPage);

        Page<Vacancy> actualPage = vacancyController.getVacancies(0, 10, new String[]{"id", "desc"});

        verify(vacancyService).findSorted(0, 10, new String[]{"id", "desc"});

        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testGetLocationStats() {
        Vacancy first = new Vacancy();
        first.setLocation("New York");
        Vacancy second = new Vacancy();
        second.setLocation("Los Angeles");
        Vacancy third = new Vacancy();
        third.setLocation("New York");
        List<Vacancy> vacancies = Arrays.asList(first, second, third);
        when(vacancyService.findAll()).thenReturn(vacancies);

        Map<String, Long> actualResult = vacancyController.getLocationStats();

        verify(vacancyService).findAll();

        Map<String, Long> expectedResult = new HashMap<>();
        expectedResult.put("New York", 2L);
        expectedResult.put("Los Angeles", 1L);
        assertEquals(expectedResult, actualResult);
    }
}