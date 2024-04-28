package com.example.QuanLyBanSach.controller;

import com.example.QuanLyBanSach.model.Account;
import com.example.QuanLyBanSach.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    // Create account
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        Account newAccount = accountService.addAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    // Update account
    @PostMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account existingAccount = accountService.getAccountById(account.getId());
        if (existingAccount == null) {
            return new ResponseEntity("Account not found", HttpStatus.NOT_FOUND);
        }
        existingAccount.setId(account.getId());
        existingAccount.setUsername(account.getUsername());
        existingAccount.setPassword(encoder.encode(account.getPassword()));
        existingAccount.setRole(account.getRole());
        Account updatedAccount = accountService.updateAccount(account);

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    // Delete account
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestParam Long id) {
        if (accountService.getAccountById(id) == null) {
            return new ResponseEntity("Account not found", HttpStatus.NOT_FOUND);
        }
        accountService.deleteAccount(id);
        return new ResponseEntity(HttpResponse.BodyHandlers.ofString(), HttpStatus.OK);
    }

    // Get account by id
    @GetMapping("/get")
    public ResponseEntity<Account> getAccountById(@RequestParam Long id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return new ResponseEntity("Account not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    // Get all accounts
    @GetMapping("/get/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Get account by username
    @GetMapping("/get/username/{username}")
    public ResponseEntity<Account> getAccountByUsername(@RequestParam String username) {
        Account account = accountService.getAccountByUsername(username);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
