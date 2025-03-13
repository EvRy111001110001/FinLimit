package com.evry.FinLimit.controllers;

import com.evry.FinLimit.model.LimitResponseDTO;
import com.evry.FinLimit.model.LimitRequestDTO;
import com.evry.FinLimit.model.TransactionWithLimitDTO;
import com.evry.FinLimit.services.LimitService;
import com.evry.FinLimit.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Client API")
public class ClientController {
    private final TransactionService transactionService;
    private final LimitService limitService;

    @Operation(summary = "getting transactions exceeded the limit by ID")
    @GetMapping("/transactions-exceeded-limit/{accountId}")
    public ResponseEntity<List<TransactionWithLimitDTO>> getTransactionsExceedingLimits(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsExceedingLimits(accountId));
    }

    @Operation(summary = "getting all established limits by ID")
    @GetMapping("/limits/{accountId}")
    public ResponseEntity<Collection<LimitResponseDTO>> getAllLimit(@PathVariable Long accountId) {
        return ResponseEntity.ok(limitService.getAllLimit(accountId));
    }

    @Operation(summary = "setting a new limit")
    @PostMapping("/limits/{accountId}")
    public ResponseEntity<Void> settingNewLimit(
            @PathVariable Long accountId,
            @RequestBody LimitRequestDTO requestDTO) {
        limitService.createNewLimit(accountId, requestDTO.getLimitSum());
        return ResponseEntity.ok().build();
    }
}
