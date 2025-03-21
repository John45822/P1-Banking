package Main;

import java.util.HashMap;
import java.util.Map;
import Accounts.Account; // Importing Account class
import Accounts.SavingsAccount; // Importing SavingsAccount class
import Accounts.CreditAccount; // Importing CreditAccount class
import Accounts.InsufficientFundsException; // Importing InsufficientFundsException class
import Accounts.Transaction; // Importing Transaction class


public class AccountManager {
    // Method to process credit payments

    // Method to handle internal fund transfers
    public void transferFunds(String senderAccountNumber, String recipientAccountNumber, double amount) {
        Account senderAccount = accounts.get(senderAccountNumber);
        SavingsAccount recipientAccount = (SavingsAccount) accounts.get(recipientAccountNumber);
        
        if (senderAccount == null || recipientAccount == null) {
            System.out.println("[Error] Invalid account number(s).");
            System.out.println(); // Prints a blank line
            return;
        }

        try {
            if (senderAccount.getBalance() < amount) {
                System.out.println("[Error] Insufficient balance in Sender Account " + senderAccountNumber + ". Available: " + senderAccount.getBalance());
                System.out.println(); // Prints a blank line
                return;
            }

            ((SavingsAccount) senderAccount).withdraw(amount);
            recipientAccount.deposit(amount); 

            // Create a transaction record for the fund transfer
            Transaction transaction = new Transaction(senderAccountNumber, Transaction.Transactions.FundTransfer, "Transferred " + amount + " to " + recipientAccountNumber);
            ((SavingsAccount) senderAccount).addTransaction(transaction); // Add transaction to sender's history
            ((SavingsAccount) recipientAccount).addTransaction(transaction); // Add transaction to recipient's history



        } catch (InsufficientFundsException e) {
            System.out.println("[Error] Insufficient funds for withdrawal.");
        }
    }

    public void processCreditPayment(String creditAccountNumber, String savingsAccountNumber, double amount) {
        Account creditAccount = accounts.get(creditAccountNumber);
        SavingsAccount savingsAccount = (SavingsAccount) accounts.get(savingsAccountNumber);
        
        if (creditAccount == null || savingsAccount == null) {
            System.out.println("[Error] Invalid account number(s).");
            System.out.println(); // Prints a blank line
            return;
        }

        try {
            if (savingsAccount.getBalance() < amount) {
                System.out.println("[Error] Insufficient balance in Savings Account " + savingsAccountNumber + ". Available: " + savingsAccount.getBalance());
                System.out.println(); // Prints a blank line
                return;
            }

            savingsAccount.withdraw(amount);
            creditAccount.deposit(amount); // Assuming a method exists to handle payments

            System.out.println("[Success] " + amount + " credited from Savings Account " + savingsAccountNumber + " to pay off Loan.");
            System.out.println("New Credit Balance: " + creditAccount.getBalance());
            System.out.println("New Savings Balance: " + savingsAccount.getBalance());
            System.out.println("Returning to Main Menu...");
            System.out.println(); // Prints a blank line
        } catch (InsufficientFundsException e) {
            System.out.println("[Error] Insufficient funds for withdrawal.");
            System.out.println(); // Prints a blank line
        }
    }

    // Method to process credit compensations
    public void processCreditCompensation(String creditAccountNumber, double amount) {
        Account creditAccount = accounts.get(creditAccountNumber);
        if (creditAccount == null) {
            System.out.println("[Error] Invalid credit account number.");
            System.out.println(); // Prints a blank line
            return;
        }

        String result = ((CreditAccount) creditAccount).applyCompensation(amount);
        System.out.println(result);
    }

    private Map<String, Account> accounts = new HashMap<>();

    public Map<String, Account> getAllAccounts() {
        return accounts;
    }

    public Map<String, Account> getAccountsByType(String accountType) {
        Map<String, Account> filteredAccounts = new HashMap<>();
        for (Account account : accounts.values()) {
            if (account.getAccountType().equalsIgnoreCase(accountType)) {
                filteredAccounts.put(account.getAccountNumber(), account);
            }
        }
        return filteredAccounts;
    }

    public boolean createAccount(String accountNumber, String accountHolderName, String accountType, double initialDeposit, String password, String pin, double creditLimit) {
        if (accounts.containsKey(accountNumber)) {
            return false; // Account already exists
        }
        Account newAccount;
        if (accountType.equalsIgnoreCase("savings")) {
            newAccount = new SavingsAccount(accountNumber, accountHolderName, initialDeposit, password, pin);
        } else if (accountType.equalsIgnoreCase("credit")) {
            newAccount = new CreditAccount(accountNumber, accountHolderName, creditLimit, password, pin);
        } else {
            return false; // Invalid account type
        }
        accounts.put(accountNumber, newAccount);
        return true;
    }

    public boolean validateAccount(String accountNumber, String password) {
        Account account = accounts.get(accountNumber);
        return account != null && account.validatePassword(password);
    }

    public boolean validatePin(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        if (account instanceof SavingsAccount) {
            return ((SavingsAccount) account).validatePassword(pin);
        }
        return false; // Invalid account type or account not found
    }

    public void withdraw(String accountNumber, double amount) throws InsufficientFundsException {
        Account account = accounts.get(accountNumber);
        if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).withdraw(amount);
        } else {
            throw new IllegalArgumentException("Invalid account type for withdrawal.");
        }
    }

    public double getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }

    public double getMaxWithdrawalLimit() {
        return 1000.0; // Example limit, adjust as necessary
    }

    public double getMaxDepositLimit() {
        return 5000.0; // Example limit, adjust as necessary
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).deposit(amount);
        } else {
            throw new IllegalArgumentException("Invalid account type for deposit.");
        }
    }

    public String getAccountHolderName(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            return account.getAccountHolderName();
        }
        return null; // or throw an exception if preferred
    }
}
