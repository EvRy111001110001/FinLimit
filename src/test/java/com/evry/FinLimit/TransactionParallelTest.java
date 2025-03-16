package com.evry.FinLimit;

import com.evry.FinLimit.entity.CurrencyShortname;
import com.evry.FinLimit.entity.Limit;
import com.evry.FinLimit.mappers.TransactionMapper;
import com.evry.FinLimit.model.TransactionDTO;
import com.evry.FinLimit.repositories.LimitRepository;
import com.evry.FinLimit.repositories.TransactionRepository;
import com.evry.FinLimit.services.ExchangeRateService;
import com.evry.FinLimit.services.TransactionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 */

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
@Transactional
public class TransactionParallelTest {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LimitRepository limitRepository;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionService transactionService;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testParallelSave() throws InterruptedException, ExecutionException {
        long countBeforeTr = transactionRepository.count();
        long countBeforeL = limitRepository.count();
        int numThreads = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            futures.add(executorService.submit(() -> {
                TransactionDTO exTransactionDTO = new TransactionDTO();
                exTransactionDTO.setAccountFrom(123L);
                exTransactionDTO.setAccountTo(2001L);
                exTransactionDTO.setCurrencyName(CurrencyShortname.KZT);
                exTransactionDTO.setSum(BigDecimal.valueOf(50));
                exTransactionDTO.setExpenseCategory("11");
                exTransactionDTO.setDatetime(LocalDateTime.now());

                transactionService.save(exTransactionDTO);
                return null;
            }));
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException("Ошибка в одном из потоков!", e);
            }
        }

        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("Не все потоки завершились!");
        }


        List<Limit> limits = limitRepository.findAll();
        Set<Long> limitIds = limits.stream().map(Limit::getId).collect(Collectors.toSet());
        long countAfterTr = transactionRepository.count();
        long countAfterL = limitRepository.count();


        assertEquals(countBeforeTr + numThreads, countAfterTr, "Все транзакции должны сохраниться!");
        assertEquals(countBeforeL + 1, countAfterL);
        assertEquals(limits.size(), limitIds.size(), "Не должно быть дубликатов в таблице лимитов!");
    }
}
