package com.evry.FinLimit.services;

import com.evry.FinLimit.entity.CurrencyShortname;
import com.evry.FinLimit.entity.Limit;
import com.evry.FinLimit.mappers.LimitMapper;
import com.evry.FinLimit.model.LimitResponseDTO;
import com.evry.FinLimit.repositories.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**

 */
@RequiredArgsConstructor
@Service
public class LimitService {
    private final LimitRepository limitRepository;
    private final LimitMapper limitMapper;

    public void createNewLimit(Long accountId, BigDecimal limitSum) {
        Limit limit = new Limit();
        limit.setAccountFrom(accountId);
        limit.setLimitCurrencyShortname(CurrencyShortname.USD);
        limit.setLimitSum(limitSum);
        limit.setLimitDatetime(LocalDateTime.now());
        limitRepository.save(limit);
    }

    public List<LimitResponseDTO> getAllLimit(Long accountId) {
        return limitRepository.findByAccountIdOrderByDateAsc(accountId).stream()
                .map(limitMapper::toDTO)
                .collect(Collectors.toList());
    }
}
