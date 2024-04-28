package com.example.QuanLyBanSach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private TransactionType type;
    @Column
    private String date;
    @Column
    private TransactionStatus status;
    @Column
    private PaymentMethod paymentMethod;
    @Column
    private Long totalPrice;
    @Column
    private Long supplierId;
    @Column
    private Long customerId;
    @Column
    private String address;
    @Column
    private Long employeeId;
}
