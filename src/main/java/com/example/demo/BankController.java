package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @PostMapping
    public void createBank(@RequestBody BankAccount requestDTO) {
        // Use the requestDTO to create the bank object
        BankAccount bank = new BankAccount(
            requestDTO.getOwnerName(),
            requestDTO.getCity(),
            requestDTO.getState(),
            requestDTO.getPin(),
            requestDTO.getBalance(),
            requestDTO.getOverdraftbalance(),
            requestDTO.getAccountType(),
            requestDTO.getCreatedDate(),
            requestDTO.getStatus()
        );
        BankAccount createdBank = bankService.createBank(bank);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getBankById(@PathVariable int id) {
        BankAccount bank = bankService.getBankById(id);
        if (bank == null) {
            throw new BankNotFoundException("Bank with ID " + id + " not found");
        }
        return ResponseEntity.ok(bank);
    }

    @GetMapping
    public Iterable<BankAccount> getAllBanks() {
        return (Iterable<BankAccount>) bankService.getAllBanks();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable int id) {
        boolean deleted = bankService.deleteBank(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new BankNotFoundException("Bank with ID " + id + " not found");
        }
    }
    

    @PutMapping("/{id}/update-all")
    public ResponseEntity<BankAccount> updateAllFields(
            @PathVariable int id,
            @RequestBody BankAccount updatedBankData) {
        // Retrieve the existing bank account from the database
        BankAccount existingBank = bankService.getBankById(id);

        // Check if the bank account with the given ID exists
        if (existingBank == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 Not Found if not found
        }

        // Update all fields with the values from updatedBankData
        existingBank.setOwnerName(updatedBankData.getOwnerName());
        existingBank.setCity(updatedBankData.getCity());
        existingBank.setState(updatedBankData.getState());
        existingBank.setPin(updatedBankData.getPin());
        existingBank.setBalance(updatedBankData.getBalance());
        existingBank.setOverdraftbalance(updatedBankData.getOverdraftbalance());
        existingBank.setAccountType(updatedBankData.getAccountType());
        existingBank.setCreatedDate(updatedBankData.getCreatedDate());
        existingBank.setStatus(updatedBankData.getStatus());

        try {
            // Save the updated bank account
            BankAccount updatedBank = bankService.updateBank(id, existingBank);
            return new ResponseEntity<>(updatedBank, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle any errors gracefully
        }
    }

    @PostMapping("/add-balance/{id}")
    public ResponseEntity<String> addBalance(
            @PathVariable int id,
            @RequestParam float amountParam) {
        if (amountParam <= 0) {
            return new ResponseEntity<>("Amount must be positive for adding balance.", HttpStatus.BAD_REQUEST);
        }
        
        try {
            bankService.addBalance(id, amountParam);
            return new ResponseEntity<>("Balance added successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Withdraw Balance from a Bank Account
    @PostMapping("/withdraw-balance/{id}")
    public ResponseEntity<String> withdrawBalance(
            @PathVariable int id,
            @RequestParam float amountParam) {
        if (amountParam <= 0) {
            return new ResponseEntity<>("Amount must be positive for withdrawing balance.", HttpStatus.BAD_REQUEST);
        }
        
        try {
            bankService.withdrawBalance(id, amountParam);
            return new ResponseEntity<>("Balance withdrawn successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
