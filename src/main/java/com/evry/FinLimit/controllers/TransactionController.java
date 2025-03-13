package com.evry.FinLimit.controllers;

import com.evry.FinLimit.model.TransactionDTO;
import com.evry.FinLimit.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Transaction API")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "for accepting transactions")
    @PostMapping("/transaction")
    public ResponseEntity<Void> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.save(transactionDTO);
        return ResponseEntity.ok().build();
    }

}
