package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.BankNotFoundException;
import com.example.demo.InsufficientBalanceException;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BankServiceImpl implements BankService {
	@Autowired
	private BankRepository bankRepository;

	@Override
	public BankAccount createBank(BankAccount bank) {
		return bankRepository.save(bank);
	}

	@Override
	public BankAccount getBankById(int id) {
		Optional<BankAccount> bank = bankRepository.findById(id);
		return bank.orElseThrow(() -> new BankNotFoundException("Bank with ID " + id + " not found"));
	}

	@Override
	public List<BankAccount> getAllBanks() {
		return StreamSupport.stream(bankRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public boolean deleteBank(int id) {
		if (bankRepository.existsById(id)) {
			bankRepository.deleteById(id);
			return true;
		} else {
			throw new BankNotFoundException("Bank with ID " + id + " not found");
		}
	}

	@Override
	public BankAccount updateBank(int id, BankAccount bank) {
		// Implement the update logic here
		// Make sure to handle exceptions and save the updated bank entity

		// Example implementation:
		BankAccount existingBank = bankRepository.findById(id).orElse(null);
		if (existingBank != null) {
			// Update all fields
			existingBank.setOwnerName(bank.getOwnerName());
			existingBank.setCity(bank.getCity());
			existingBank.setState(bank.getState());
			existingBank.setPin(bank.getPin());
			existingBank.setBalance(bank.getBalance());
			existingBank.setOverdraftbalance(bank.getOverdraftbalance());
			existingBank.setAccountType(bank.getAccountType());
			existingBank.setCreatedDate(bank.getCreatedDate());
			existingBank.setStatus(bank.getStatus());

			// Handle other field updates as needed

			// Save the updated bank entity
			return bankRepository.save(existingBank);
		} else {
			throw new EntityNotFoundException("Bank with ID " + id + " not found");
		}
	}

	@Override
	public void addBalance(int id, float amountParam) {
		Optional<BankAccount> optionalBank = bankRepository.findById(id);

		if (optionalBank.isPresent()) {
			BankAccount bank = optionalBank.get();

			// Check if the account is active
			if (bank.getStatus() == AccountStatus.ACTIVE) {
				float currentBalance = bank.getBalance();
				float newBalance = currentBalance + amountParam;
				bank.setBalance(newBalance);
				bankRepository.save(bank);
			} else {
				throw new InactiveAccountException("Account is inactive or closed and cannot add balance.");
			}
		} else {
			throw new BankNotFoundException("Bank account not found with ID: " + id);
		}
	}

	@Override
	public void withdrawBalance(int id, float amountParam) {
		Optional<BankAccount> optionalBank = bankRepository.findById(id);

		if (optionalBank.isPresent()) {
			BankAccount bank = optionalBank.get();

			// Check if the account is active
			if (bank.getStatus() == AccountStatus.ACTIVE) {
				float currentBalance = bank.getBalance();

				if (currentBalance >= amountParam) {
					float newBalance = currentBalance - amountParam;
					bank.setBalance(newBalance);
					bankRepository.save(bank);
				} else {
					throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
				}
			} else {
				throw new InactiveAccountException("Account is inactive or closed and cannot perform withdrawals.");
			}
		} else {
			throw new BankNotFoundException("Bank account not found with ID: " + id);
		}
	}

}
