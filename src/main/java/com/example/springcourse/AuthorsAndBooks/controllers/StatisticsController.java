package com.example.springcourse.AuthorsAndBooks.controllers;

import com.example.springcourse.AuthorsAndBooks.dto.ResponseDto;
import com.example.springcourse.AuthorsAndBooks.dto.StatisticsDto;
import com.example.springcourse.AuthorsAndBooks.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private StatisticService statisticService;

    @Autowired
    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/{id}")
    public ResponseDto<StatisticsDto> getStatistics(@PathVariable Long id) {
        StatisticsDto statistics = statisticService.getStatistic(id);
        if (statistics == null) {
            return new ResponseDto<>("data was not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseDto<>(statistics, "statistic", HttpStatus.OK);
    }
}
