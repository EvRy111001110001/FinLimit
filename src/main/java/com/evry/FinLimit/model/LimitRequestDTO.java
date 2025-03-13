package com.evry.FinLimit.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 */
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Limit request")
public class LimitRequestDTO {
    private BigDecimal limitSum;
}
