package Accounts;

import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account {
    private String password; // Added to track the account balance
    private List<Transaction> transactionHistory; // List to store transaction history

    public SavingsAccount(String accountNumber, String accountHolderName, double initialDeposit, String password, String pin) {
        super(accountNumber, accountHolderName, initialDeposit, pin); // Pass the PIN to the superclass constructor

        this.password = password;
        this.transactionHistory = new ArrayList<>(); // Initialize the transaction history
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    
    // Method to deposit funds
    public void deposit(double amount) {
        if (amount > 0) {
            super.deposit(amount); // Update balance in Account class
            // Create a transaction record for the deposit
            Transaction transaction = new Transaction(this.getAccountNumber(), Transaction.Transactions.Deposit, "Deposited " + amount);
            addTransaction(transaction); // Add transaction to history
        }
    }

    // Method to withdraw funds
    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        super.withdraw(amount); // Use the inherited withdraw method to update the balance
        // Create a transaction record for the withdrawal
        Transaction transaction = new Transaction(this.getAccountNumber(), Transaction.Transactions.Withdraw, "Withdrew " + amount);
        addTransaction(transaction); // Add transaction to history
    }

    // Method to transfer funds to another account
    public void transfer(SavingsAccount targetAccount, double amount) {
        try {
            withdraw(amount); // Deduct from sender
            targetAccount.deposit(amount); // Add to receiver
            // Create a transaction record for the fund transfer
            Transaction transaction = new Transaction(this.getAccountNumber(), Transaction.Transactions.FundTransfer, "Transferred " + amount + " to " + targetAccount.getAccountNumber());
            addTransaction(transaction); // Add transaction to history
        } catch (InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage()); // Handle insufficient funds case
        }
    }

    // Method to get the current balance
    @Override
    public double getBalance() {
        return super.getBalance(); // Return the correct balance from Account class
    }
    
    @Override
    public String getAccountType() {
        return "Savings";
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
