package com.capstoneproject.transactionservice.controller;

import com.capstoneproject.transactionservice.dto.TransactionRequest;
import com.capstoneproject.transactionservice.dto.TransactionResponse;
import com.capstoneproject.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.createTransaction(transactionRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        transactionService.updateTransaction(id,transactionRequest);
    }

    // Get an transaction by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

}
