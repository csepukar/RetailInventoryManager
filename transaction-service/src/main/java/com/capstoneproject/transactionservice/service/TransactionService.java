package com.capstoneproject.transactionservice.service;

import com.capstoneproject.transactionservice.dto.TransactionRequest;
import com.capstoneproject.transactionservice.dto.TransactionResponse;
import com.capstoneproject.transactionservice.model.Transaction;
import com.capstoneproject.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public void createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .userId(transactionRequest.getUserId())
                .orderId(transactionRequest.getOrderId())
                .code(transactionRequest.getCode())
                .type(transactionRequest.getType())
                .mode(transactionRequest.getMode())
                .status(transactionRequest.getStatus())
                .content(transactionRequest.getContent())
                .createdAt(transactionRequest.getCreatedAt())
                .updatedAt(transactionRequest.getUpdatedAt())
                .build();

        // Setting createdBy based on userId
        transaction.setCreatedBy(getUserNameFromUserId(transactionRequest.getUserId()));

        transactionRepository.save(transaction);
        log.info("Order {} is saved", transaction.getId());
    }

    private String getUserNameFromUserId(Long userId) {
        return "ram";
    }


    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> categories = transactionRepository.findAll();

        return categories.stream().map(this::mapToTransactionResponse).toList();
    }

    public TransactionResponse getTransactionById(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return optionalTransaction.isPresent() ? optionalTransaction.map(this::mapToTransactionResponse).get() : null;
    }

    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }

    public void updateTransaction(Long id, TransactionRequest transactionRequest) {
        transactionRepository.findById(id).ifPresent(transaction -> {
            Transaction updatedTransaction = Transaction.builder()
                    .id(transaction.getId())
                    .userId(Optional.ofNullable(transactionRequest.getUserId()).orElse(transaction.getUserId()))
                    .orderId(Optional.ofNullable(transactionRequest.getOrderId()).orElse(transaction.getOrderId()))
                    .code(Optional.ofNullable(transactionRequest.getCode()).orElse(transaction.getCode()))
                    .type(Optional.ofNullable(transactionRequest.getType()).orElse(transaction.getType()))
                    .mode(Optional.ofNullable(transactionRequest.getMode()).orElse(transaction.getMode()))
                    .status(Optional.ofNullable(transactionRequest.getStatus()).orElse(transaction.getStatus()))
                    .content(Optional.ofNullable(transactionRequest.getContent()).orElse(transaction.getContent()))
                    .createdBy(transaction.getCreatedBy())
                    .updatedBy("shyam") // change the name with the login credential
                    .createdAt(transaction.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            transactionRepository.saveAndFlush(updatedTransaction);
            log.info("Transaction {} is updated", updatedTransaction.getId());
        });
    }


    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .userId(transaction.getUserId())
                .orderId(transaction.getOrderId())
                .code(transaction.getCode())
                .type(transaction.getType())
                .mode(transaction.getMode())
                .status(transaction.getStatus())
                .content(transaction.getContent())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .createdBy(transaction.getCreatedBy())
                .updatedBy(transaction.getUpdatedBy())
                .build();
    }
}
