package com.capstoneproject.transactionservice.repository;

import com.capstoneproject.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
