package Launchers;

import Accounts.SavingsAccount;
import Accounts.Account;
import Accounts.InsufficientFundsException;

public class SavingsAccountLauncher extends AccountLauncher {

    @Override
    public void accountLogin() {
        System.out.print(" Enter your account number: ");
        String accountNum = scanner.nextLine();

        System.out.print(" Enter your 4-digit PIN: ");
        String pin = scanner.nextLine();

        Account account = checkCredentials(accountNum, pin);

        // Ensure the account is a Savings Account before proceeding
        if (account instanceof SavingsAccount) {
            setLogSession(account);
            System.out.println(" Welcome back, " + account.getAccountHolderName() + "! Your Savings Account is now active.");
        } else {
            System.out.println(" Oops! The credentials are incorrect or this isn't a Savings Account.");
        }
    }

    // Initialize Savings Account interface after login
    public void savingsAccountInit() {
        if (!isLoggedIn()) {
            System.out.println(" No account is currently logged in. Please log in to get started.");
            return;
        }

        // Check if the logged-in account is a Savings Account
        if (loggedAccount instanceof SavingsAccount) {
            System.out.println(" Your Savings Account is ready, " + loggedAccount.getAccountHolderName() + ".");
        } else {
            System.out.println(" It looks like this is not a Savings Account. Please double-check.");
        }
    }

    // Handle deposit process
    public void depositProcess(double amount) {
        if (!isLoggedIn()) {
            System.out.println(" You're not logged in. Please log in to make a deposit.");
            return;
        }

        // Confirm that the logged account is a Savings Account before deposit
        if (loggedAccount instanceof SavingsAccount) {
            ((SavingsAccount) loggedAccount).deposit(amount);
            System.out.println(" Deposit of $" + amount + " was successful! New balance: $" + loggedAccount.getBalance());
        } else {
            System.out.println(" This account does not support deposits.");
        }
    }

    // Handle withdrawal process
    public void withdrawProcess(double amount) {
        if (!isLoggedIn()) {
            System.out.println(" You're not logged in. Please log in to withdraw funds.");
            return;
        }

        try {
            // Check if the logged account is a Savings Account before withdrawal
            if (loggedAccount instanceof SavingsAccount) {
                ((SavingsAccount) loggedAccount).withdraw(amount);
                System.out.println(" Withdrawal of $" + amount + " was successful! Remaining balance: $" + loggedAccount.getBalance());
            } else {
                System.out.println(" This account is not configured for withdrawals.");
            }
        } catch (InsufficientFundsException e) {
            System.out.println(" Sorry, insufficient funds to complete the transaction. Current balance: $" + loggedAccount.getBalance());
        }
    }

    // Handle fund transfer process
    public void fundTransferProcess(SavingsAccount targetAccount, double amount) throws InsufficientFundsException {
        if (!isLoggedIn()) {
            System.out.println(" You're not logged in. Please log in to transfer funds.");
            return;
        }

        // Confirm the account type before transferring funds
        if (loggedAccount instanceof SavingsAccount) {
            SavingsAccount sourceAccount = (SavingsAccount) loggedAccount;
            if (targetAccount == null) {
                System.out.println(" The target account is invalid. Please check the account details and try again.");
                return;
            }

            sourceAccount.transfer(targetAccount, amount);
            System.out.println(" Successfully transferred $" + amount + " to account: " + targetAccount.getAccountNumber());
            System.out.println(" Your remaining balance is: $" + sourceAccount.getBalance());
        } else {
            System.out.println(" This account is not set up for fund transfers.");
        }
    }

    // Get the Savings Account of the currently logged-in user
    public SavingsAccount getLoggedAccount() {
        if (!isLoggedIn()) {
            System.out.println(" No account is currently logged in. Please log in first.");
            return null;
        }

        // Check if the logged-in account is a Savings Account
        if (loggedAccount instanceof SavingsAccount) {
            return (SavingsAccount) loggedAccount;
        } else {
            System.out.println(" The currently logged-in account is not a Savings Account.");
            return null;
        }
    }
}
