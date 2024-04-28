package com.example.QuanLyBanSach.repository;

import com.example.QuanLyBanSach.model.Transaction;
import com.example.QuanLyBanSach.model.TransactionStatus;
import com.example.QuanLyBanSach.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByStatus(TransactionStatus status);

    List<Transaction> findByDate(String date);
}
