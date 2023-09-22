package com.example.demo;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "accountdetailstable")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Owner name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Owner name should only contain letters and spaces")
    private String ownerName;
    @NotBlank(message = "City is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City should only contain letters and spaces")
    private String city;
    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "State should only contain letters")
    private String state;
    @NotNull(message = "Pin is required")
    @Min(value = 100000, message = "Pin should be a 6-digit number")
    @Max(value = 999999, message = "Pin should be a 6-digit number")
    private int pin;
    @DecimalMin(value = "0.0", message = "Balance cannot be negative")
    @NumberFormat(style = Style.NUMBER)
    private float balance;
    @DecimalMin(value = "0.0", message = "Balance cannot be negative")
    @NumberFormat(style = Style.NUMBER)
    @NotNull(message = "Overdraft balance is required")
    private float overdraftbalance;
    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "savings|current", message = "Account type should be 'savings' or 'current'")
    private String account_type; // Add accountType field
    @NotNull(message = "Created date is required")
    @CreationTimestamp
    private Date createdDate;
    @NotNull(message = "Account status is required")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    public BankAccount() {
    }

    public BankAccount(String ownerName, String city, String state, int pin, float balance, float overdraftbalance,
                String accountType, Date createdDate, AccountStatus status) {
        this.ownerName = ownerName;
        this.city = city;
        this.state = state;
        this.pin = pin;
        this.balance = balance;
        this.overdraftbalance = overdraftbalance;
        this.account_type = accountType;
        this.createdDate = createdDate;
        this.status = status;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getOverdraftbalance() {
        return overdraftbalance;
    }

    public void setOverdraftbalance(float overdraftbalance) {
        this.overdraftbalance = overdraftbalance;
    }

    public String getAccountType() {
        return account_type;
    }

    public void setAccountType(String accountType) {
        this.account_type = accountType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
