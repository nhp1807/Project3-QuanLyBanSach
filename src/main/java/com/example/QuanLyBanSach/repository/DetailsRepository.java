package com.example.QuanLyBanSach.repository;

import com.example.QuanLyBanSach.model.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Long> {
    List<Details> findByTransactionId(Long transactionId);
}
