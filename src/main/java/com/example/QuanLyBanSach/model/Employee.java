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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private EmployeeType type;
    @Column
    private String name;
    @Column
    private Gender gender;
    @Column
    private String dob;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private Long accountId;
}
