package Launchers;

import Accounts.Account;
import Bank.Bank;
import java.util.Scanner;

public class AccountLauncher {

    // Account object of the currently logged-in account user
    protected Account loggedAccount;
    // Selected associated bank when attempting to log in
    protected Bank assocBank;
    protected BankLauncher bankLauncher = new BankLauncher();
    protected Scanner scanner = new Scanner(System.in);

    // Check if an account is currently logged in
    public boolean isLoggedIn() {
        return loggedAccount != null;
    }

    // Account login with credential checks
    public void accountLogin() {
        if (assocBank == null) {
            System.out.println(" No bank selected. Please select a bank before logging in.");
            return;
        }

        System.out.print(" Enter your account number: ");
        String accountNum = scanner.nextLine();

        System.out.print(" Enter your 4-digit PIN: ");
        String pin = scanner.nextLine();

        // Verify credentials and attempt login
        Account account = checkCredentials(accountNum, pin);
        if (account != null) {
            setLogSession(account);
            System.out.println(" Hello, " + loggedAccount.getAccountHolderName() + "! You’ve successfully logged into your account.");
        } else {
            System.out.println(" Invalid account number or PIN. Please try again.");
        }
    }

    // Bank selection before account login
    public Bank selectBank() {
        bankLauncher.showBanksMenu(); // Show available banks
        System.out.print(" Enter the bank name to proceed: ");
        String bankName = scanner.nextLine();

        // Attempt to select the bank
        for (Bank bank : bankLauncher.getBanks()) {
            if (bank.getName().equalsIgnoreCase(bankName)) {
                assocBank = bank;
                System.out.println(" Bank selected: " + assocBank.getName());
                return assocBank;
            }
        }

        System.out.println(" Bank not found. Please try again.");
        return null;
    }

    // Create a login session for the logged-in account
    public void setLogSession(Account account) {
        this.loggedAccount = account;
    }

    // Destroy the current login session
    public void destroyLogSession() {
        if (loggedAccount != null) {
            System.out.println(" Logged out successfully from account: " + loggedAccount.getAccountHolderName());
        } else {
            System.out.println(" No account is currently logged in.");
        }
        loggedAccount = null;
    }

    // Check credentials and return account if valid
    public Account checkCredentials(String accountNum, String pin) {
        if (assocBank == null) {
            System.out.println(" No associated bank selected. Please select a bank before logging in.");
            return null;
        }

        Account account = assocBank.getAccount(accountNum);

        // Verify the account and credentials
        if (account != null && account.validatePassword(pin)) {
            return account;
        }
        return null;
    }
}
