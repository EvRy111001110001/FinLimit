package com.evry.FinLimit;

import com.evry.FinLimit.entity.CurrencyShortname;
import com.evry.FinLimit.entity.Limit;
import com.evry.FinLimit.mappers.TransactionMapper;
import com.evry.FinLimit.model.TransactionWithLimitDTO;
import com.evry.FinLimit.repositories.LimitRepository;
import com.evry.FinLimit.repositories.TransactionRepository;
import com.evry.FinLimit.services.ExchangeRateService;
import com.evry.FinLimit.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private LimitRepository limitRepository;
    @Mock
    private ExchangeRateService exchangeRateService;
    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(transactionRepository,limitRepository,
                exchangeRateService,transactionMapper);
    }

    @Test
    void testFindRelevantLimit() {
        List<Limit> limits = Arrays.asList(
                new Limit(1L,1001L, BigDecimal.valueOf(100), LocalDateTime.of(2023, 3, 1, 0, 0),
                        CurrencyShortname.USD),
                new Limit(2L,1001L,BigDecimal.valueOf(200), LocalDateTime.of(2024, 3, 5, 0, 0),
                        CurrencyShortname.USD),
                new Limit(3L,1001L,BigDecimal.valueOf(300), LocalDateTime.of(2025, 3, 10, 0, 0),
                        CurrencyShortname.USD)
        );

        TransactionWithLimitDTO transaction = new TransactionWithLimitDTO(
                1L,1001L,2001L,"KZT", BigDecimal.valueOf(50),
                "11" , Timestamp.valueOf("2025-01-01 09:00:00"),
                Timestamp.valueOf("2024-03-05 09:00:00"),BigDecimal.valueOf(200),"USD");

        Limit relevantLimit = transactionService.findRelevantLimit(transaction, limits);

        assertNotNull(relevantLimit);
        assertEquals(BigDecimal.valueOf(200), relevantLimit.getLimitSum());
    }

    @Test
    void testDebtRepayment() {
        List<BigDecimal> debts = new ArrayList<>();
        debts.add(BigDecimal.valueOf(50));
        debts.add(BigDecimal.valueOf(30));

        BigDecimal limitSum = BigDecimal.valueOf(100);
        BigDecimal remainingLimit = transactionService.debtRepayment(limitSum, debts);

        assertEquals(BigDecimal.valueOf(20), remainingLimit);
        assertTrue(debts.isEmpty());
    }

    @Test
    void testCalculateRemainingLimits() {
        List<TransactionWithLimitDTO> transactions = Arrays.asList(
                new TransactionWithLimitDTO(
                        1L,1001L,2001L,"KZT", BigDecimal.valueOf(50),
                        "11" , Timestamp.valueOf("2025-01-01 09:00:00"),
                        Timestamp.valueOf("2024-01-01 09:00:00"),BigDecimal.valueOf(150),"USD"),
                new TransactionWithLimitDTO(
                        2L,1001L,2001L,"KZT", BigDecimal.valueOf(80),
                        "11" , Timestamp.valueOf("2025-01-01 09:00:00"),
                        Timestamp.valueOf("2024-01-01 09:00:00"),BigDecimal.valueOf(150),"USD"),
                new TransactionWithLimitDTO(
                        3L,1001L,2001L,"KZT", BigDecimal.valueOf(100),
                        "11" , Timestamp.valueOf("2025-01-01 09:00:00"),
                        Timestamp.valueOf("2024-01-01 09:00:00"),BigDecimal.valueOf(150),"USD")
        );

        List<Long> result = new ArrayList<>();
        List<BigDecimal> debts = new ArrayList<>();
        BigDecimal limit = BigDecimal.valueOf(150);

        when(exchangeRateService.getExchangeRate(anyString(), any())).thenReturn(BigDecimal.ONE);

        transactionService.calculateRemainingLimits(transactions, limit, result, debts);

        assertEquals(1, result.size());
        assertEquals(3L, result.get(0)); // Третья транзакция превышает лимит
        assertEquals(BigDecimal.valueOf(80), debts.get(0)); // Остаток долга
    }
}
