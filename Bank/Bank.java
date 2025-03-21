package Bank;

import Accounts.Account;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private String name;
    private String passcode;
    private Map<String, Account> accounts;
    private double processingFee;

    public Bank(String name, String passcode, double processingFee) {
        this.name = name;
        this.passcode = passcode;
        this.processingFee = processingFee;
        this.accounts = new HashMap<>();
    }

    public Bank(String name, String passcode) {
        this.name = name;
        this.passcode = passcode;
        this.accounts = new HashMap<>();
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public String getName() {
        return name;
    }

    public String getPasscode() {
        return passcode;
    }

    public boolean validatePasscode(String passcode) {
        return this.passcode.equals(passcode);
    }

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

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}
