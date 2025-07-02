package com.example.springcourse.AuthorsAndBooks.services;

import com.example.springcourse.AuthorsAndBooks.dto.StatisticsDto;
import com.example.springcourse.AuthorsAndBooks.dto.StatisticsRedis;
import com.example.springcourse.AuthorsAndBooks.models.Statistics;
import com.example.springcourse.AuthorsAndBooks.repositories.StatisticsRedisManualRepository;
import com.example.springcourse.AuthorsAndBooks.repositories.StatisticsRedisRepository;
import com.example.springcourse.AuthorsAndBooks.repositories.StatisticsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class StatisticService {

    private final StatisticsRepository statisticsRepository;

    private final StatisticsRedisRepository statisticsRedisRepository;

    private final StatisticsRedisManualRepository statisticsRedisManualRepository;

    @Autowired
    public StatisticService(StatisticsRepository statisticsRepository, StatisticsRedisRepository statisticsRedisRepository,
                            StatisticsRedisManualRepository statisticsRedisManualRepository) {
        this.statisticsRepository = statisticsRepository;
        this.statisticsRedisRepository = statisticsRedisRepository;
        this.statisticsRedisManualRepository = statisticsRedisManualRepository;
    }

    public StatisticsDto getStatistic(long id) {
        // Try Redis
        Optional<StatisticsRedis> optionalRedis = statisticsRedisManualRepository.getByCustomKey(id);
        if (optionalRedis.isPresent()) {
            return convertToDto(optionalRedis.get());
        }

        // Try database
        Optional<Statistics> optionalDb = statisticsRepository.findById(id);
        if (optionalDb.isPresent()) {
            Statistics dbEntity = optionalDb.get();
            statisticsRedisManualRepository.saveWithCustomKey(id, convertToRedisDto(dbEntity));
            return convertToDto(dbEntity);
        }

        // Not found in Redis or DB
        return null;
    }

    private StatisticsDto convertToDto(StatisticsRedis stat) {
        if (stat == null) {
            return null;
        }
        return new StatisticsDto(stat.getQuery(), stat.getNumbers());
    }

    private StatisticsDto convertToDto(Statistics stat) {
        if (stat == null) {
            return null;
        }
        return new StatisticsDto(stat.getQuery(), stat.getNumbers());
    }

    private StatisticsRedis convertToRedisDto(Statistics savedStatistics) {
        StatisticsRedis statisticsRedis = new StatisticsRedis();
        statisticsRedis.setId(savedStatistics.getId());
        statisticsRedis.setQuery(savedStatistics.getQuery());
        statisticsRedis.setNumbers(savedStatistics.getNumbers());
        return statisticsRedis;
    }
}
