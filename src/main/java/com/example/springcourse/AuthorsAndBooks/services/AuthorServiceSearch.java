package com.example.springcourse.AuthorsAndBooks.services;

import com.example.springcourse.AuthorsAndBooks.dto.AuthorDto;
import com.example.springcourse.AuthorsAndBooks.dto.BookDto;
import com.example.springcourse.AuthorsAndBooks.models.Author;
import com.example.springcourse.AuthorsAndBooks.models.Statistics;
import com.example.springcourse.AuthorsAndBooks.dto.StatisticsRedis;
import com.example.springcourse.AuthorsAndBooks.repositories.AuthorsRepository;
import com.example.springcourse.AuthorsAndBooks.repositories.StatisticsRedisManualRepository;
import com.example.springcourse.AuthorsAndBooks.repositories.StatisticsRedisRepository;
import com.example.springcourse.AuthorsAndBooks.repositories.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class AuthorServiceSearch {

    private static final Object LOCK = new Object();

    private final AuthorsRepository authorsRepository;

    private final StatisticsRepository statisticsRepository;

    private final StatisticsRedisRepository statisticsRedisRepository;

    private final StatisticsRedisManualRepository statisticsRedisManualRepository;

    @Value("${alert.threshold}")
    private int threshold;

    @Autowired
    public AuthorServiceSearch(AuthorsRepository authorsRepository, StatisticsRepository statisticsRepository, StatisticsRedisRepository statisticsRedisRepository, StatisticsRedisManualRepository statisticsRedisManualRepository) {
        this.authorsRepository = authorsRepository;
        this.statisticsRepository = statisticsRepository;
        this.statisticsRedisRepository = statisticsRedisRepository;
        this.statisticsRedisManualRepository = statisticsRedisManualRepository;
    }

//    private AlertRestClient arc = new AlertRestClient();

    @Transactional
    public List<AuthorDto> search(String name, Integer page, Integer authorsPerPage) {
        List<Author> authors;
        if (page == null) {
            authors = authorsRepository.findByNameContainingIgnoreCase(name);
        }else{
            authors = authorsRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, authorsPerPage));
        }
        List<AuthorDto> authorsDto = authors.stream().map(this::convertToDto).collect(Collectors.toList());
        reportStatistics(authors, name);
        return authorsDto;
    }

    private void reportStatistics(List<Author> authors, String query) {
        try {
            Optional<Statistics> optionalStatistics = statisticsRepository.findByQueryIgnoreCase(query);
            Statistics statistics;
            if (optionalStatistics.isPresent()) {
                statistics = optionalStatistics.get();
            } else {
                statistics = new Statistics(query.toLowerCase());
            }
            synchronized (LOCK) {
                statistics.setNumbers(statistics.getNumbers() + 1);
            }
            Statistics savedStatistics = statisticsRepository.save(statistics);
            StatisticsRedis statisticsRedis = convertToRedisDto(savedStatistics);

            statisticsRedisManualRepository.saveWithCustomKey(statistics.getId() ,statisticsRedis);
            CompletableFuture.runAsync(() -> sendAlert(savedStatistics, authors));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendAlert(Statistics statistics, List<Author> authors) {
        if (statistics.getNumbers() > threshold && authors.size() > threshold) {
            System.out.println("too popular search with too much data, sending an alert...");
//            arc.send(query, statistics.getNumbers(), authors.size());
        }
    }

    private AuthorDto convertToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBooks(author.getBooks().stream().map(book -> new BookDto(book.getId(), book.getName())).collect(Collectors.toList()));
        return authorDto;
    }

    private StatisticsRedis convertToRedisDto(Statistics savedStatistics) {
        StatisticsRedis statisticsRedis = new StatisticsRedis();
        statisticsRedis.setId(savedStatistics.getId());
        statisticsRedis.setQuery(savedStatistics.getQuery());
        statisticsRedis.setNumbers(savedStatistics.getNumbers());
        return statisticsRedis;
    }
}
