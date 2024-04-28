package com.example.QuanLyBanSach.service;

import com.example.QuanLyBanSach.model.Details;
import com.example.QuanLyBanSach.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsService {
    @Autowired
    private DetailsRepository detailsRepository;

    public List<Details> getAllDetails() {
        return detailsRepository.findAll();
    }

    public Details getDetailsById(Long id) {
        return detailsRepository.findById(id).orElse(null);
    }

    public Details addDetails(Details details) {
        return detailsRepository.save(details);
    }

    public Details updateDetails(Details details) {
        return detailsRepository.save(details);
    }

    public void deleteDetails(Long id) {
        detailsRepository.deleteById(id);
    }

    public List<Details> getDetailsByTransactionId(Long transactionId) {
        return detailsRepository.findByTransactionId(transactionId);
    }
}
