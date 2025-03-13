package com.evry.FinLimit.model;

import com.evry.FinLimit.entity.CurrencyShortname;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**

 */
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Limit response")
public class LimitResponseDTO {
    @JsonIgnore
    private Long id;
    private Long accountFrom;
    private BigDecimal limitSum;
    private LocalDateTime limitDatetime;
    private CurrencyShortname limitCurrencyShortname;
}
