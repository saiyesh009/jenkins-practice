package com.example.demo;

import java.awt.List;

import org.springframework.stereotype.Service;

public interface BankService {
    BankAccount createBank(BankAccount bank);
    BankAccount getBankById(int id);
    Iterable<BankAccount> getAllBanks();
    boolean deleteBank(int id);
    BankAccount updateBank(int id, BankAccount bank);
    void addBalance(int id, float amountPram) throws Exception;
    void withdrawBalance(int id, float amountPram) throws Exception;
    
}


