package Launchers;

import Bank.Bank;
import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

public class BankLauncher {

    // List of all registered banks in this session
    private static ArrayList<Bank> banks = new ArrayList<>();
    // Bank currently logged in (null if no session is active)
    private Bank loggedBank;

    private Scanner scanner = new Scanner(System.in);

    // Check if a bank is currently logged in
    public boolean isLogged() {
        return loggedBank != null;
    }

    // Bank login logic
    public void bankInit() {
        if (loggedBank == null) {
            System.out.println(" No bank is currently logged in. Please log in to continue.");
        } else {
            System.out.println(" Welcome back! You are logged into " + loggedBank.getName() + ".");
            showAccounts(); // Call to show accounts after login
        }
    }

    // Show all accounts registered to this bank
    private void showAccounts() {
        if (loggedBank == null) {
            System.out.println(" Please log in to a bank first.");
            return;
        }

        System.out.println(" Select account type to view:");
        System.out.println("[1] Credit Accounts");
        System.out.println("[2] Savings Accounts");
        System.out.println("[3] All Accounts");

        int choice = Integer.parseInt(scanner.nextLine());
        Map<String, Account> accounts;

        switch (choice) {
            case 1:
                accounts = loggedBank.getAccountsByType("Credit");
                break;
            case 2:
                accounts = loggedBank.getAccountsByType("Savings");
                break;
            case 3:
                accounts = loggedBank.getAllAccounts();
                break;
            default:
                System.out.println(" Invalid choice. Please try again.");
                return;
        }

        if (accounts.isEmpty()) {
            System.out.println(" No accounts found of the selected type.");
        } else {
            System.out.println(" Here are the accounts:");
            for (Account account : accounts.values()) {
                System.out.println(account);
            }
        }
    }

    // Create new accounts in the currently logged-in bank
    public void createNewAccount() {
        if (loggedBank == null) {
            System.out.println(" Please log in to a bank first.");
            return;
        }

        System.out.println(" Choose the type of account to create:");
        System.out.println("[1] Credit Account");
        System.out.println("[2] Savings Account");

        int choice = Integer.parseInt(scanner.nextLine());

        System.out.print(" Enter account holder's name: ");
        String holderName = scanner.nextLine();

        System.out.print(" Enter initial deposit amount: ");
        double initialAmount = Double.parseDouble(scanner.nextLine());

        System.out.print(" Create a 4-digit PIN for this account: ");
        String pin = scanner.nextLine();

        String accountNumber = "A" + (loggedBank.getAllAccounts().size() + 1);

        if (choice == 1) {
            System.out.print(" Enter credit limit: ");
            double creditLimit = Double.parseDouble(scanner.nextLine());
            CreditAccount newAccount = new CreditAccount(accountNumber, holderName, creditLimit, "password", pin); // Updated to include PIN
            loggedBank.addAccount(newAccount);
            System.out.println(" Credit Account created successfully!");
        } else if (choice == 2) {
            SavingsAccount newAccount = new SavingsAccount(accountNumber, holderName, initialAmount, "password", pin); // Updated to include PIN
            loggedBank.addAccount(newAccount);
            System.out.println(" Savings Account created successfully!");
        } else {
            System.out.println(" Invalid account type. Please try again.");
        }
    }

    // Set login session for a bank
    public void loginToBank(Bank bank) {
        this.loggedBank = bank;
    }

    // Log out from the current bank session
    public void logoutFromBank() {
        this.loggedBank = null;
        System.out.println(" You have been logged out.");
    }

    // Create a new bank entity and add it to the BANKS list
    public void registerNewBank(Bank b) {
        banks.add(b);
    }

    // Get a bank by criteria
    public Bank getBank(Comparator<Bank> bankComparator, Bank targetBank) {
        for (Bank bank : banks) {
            if (bankComparator.compare(bank, targetBank) == 0) {
                return bank;
            }
        }
        return null;
    }

    // Return the number of registered banks
    public int bankSize() {
        return banks.size();
    }

    public Bank[] getBanks() {
        return banks.toArray(new Bank[0]);
    }

    public Account getAccountByCredentials(String accountNum, String pin) {
        for (Account account : loggedBank.getAllAccounts().values()) {
            if (account.getAccountNumber().equals(accountNum) && account.getPin().equals(pin)) {
                return account;
            }
        }
        return null; // Return null if no account is found
    }

    // Method to show available banks
    public void showBanksMenu() {
        if (banks.isEmpty()) {
            System.out.println(" No banks available.");
        } else {
            System.out.println(" Available Banks:");
            for (Bank bank : banks) {
                System.out.println(" - " + bank.getName());
            }
        }
    }
}
