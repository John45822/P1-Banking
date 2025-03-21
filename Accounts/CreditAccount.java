package Accounts;

import java.util.ArrayList;
import java.util.List;

public class CreditAccount extends Account {
    private String password;
    private List<Transaction> transactionHistory; // List to store transaction history

    public CreditAccount(String accountNumber, String accountHolderName, double creditLimit, String password, String pin) {
        super(accountNumber, accountHolderName, creditLimit, pin); // Pass the PIN to the superclass constructor


        this.password = password;
        this.transactionHistory = new ArrayList<>(); // Initialize the transaction history
    }

    // Method to get transaction history
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    // Method to apply compensation to reduce loan
    public String applyCompensation(double amount) {
        if (amount <= 0) {
            return "[Error] Compensation amount must be greater than zero." + "\n";
            
        }
        
        try {
            withdraw(amount); // Ensure proper balance deduction
            Transaction transaction = new Transaction(this.getAccountNumber(), Transaction.Transactions.Recompense, "Compensation of " + amount);
            transactionHistory.add(transaction); // Add transaction to history
            return "[Success] " + amount + " has been deducted from Credit Account " + getAccountNumber() + ".\nNew Credit Balance: " + getBalance() + "\n";
            


        } catch (InsufficientFundsException e) {
            return "[Error] Not enough balance to apply compensation." + "\n";
        }
    }

    @Override
    public String getAccountType() {
        return "Credit";
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    // Override the deposit method to record transactions if needed
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        Transaction transaction = new Transaction(this.getAccountNumber(), Transaction.Transactions.Payment, "Deposited " + amount);
        transactionHistory.add(transaction); // Add transaction to history
    }
}
