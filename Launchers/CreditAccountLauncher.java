package Launchers;

import Accounts.CreditAccount;
import Accounts.Account;

public class CreditAccountLauncher extends AccountLauncher {

    @Override
    public void accountLogin() {
        System.out.print(" Enter your account number: ");
        String accountNum = scanner.nextLine();

        System.out.print(" Enter your 4-digit PIN: ");
        String pin = scanner.nextLine();

        Account account = checkCredentials(accountNum, pin);

        // Ensure the account is a Credit Account before proceeding
        if (account instanceof CreditAccount) {
            setLogSession(account);
            System.out.println(" Welcome, " + account.getAccountHolderName() + "! You’ve successfully logged into your Credit Account.");
        } else {
            System.out.println(" Oops! Either the credentials are incorrect, or this isn't a Credit Account.");
        }
    }

    // Initialize Credit Account interface after login
    public void creditAccountInit() {
        if (!isLoggedIn()) {
            System.out.println(" No account is logged in. Please log in to proceed.");
            return;
        }

        // Check if the logged-in account is a Credit Account
        if (loggedAccount instanceof CreditAccount) {
            System.out.println(" Credit Account is ready for use, " + loggedAccount.getAccountHolderName() + ".");
        } else {
            System.out.println(" Looks like this is not a Credit Account. Please double-check.");
        }
    }

    // Process a credit payment
    public void creditPaymentProcess(double amount) {
        if (!isLoggedIn()) {
            System.out.println(" You're not logged in. Please log in to make a payment.");
            return;
        }

        // Confirm account type before proceeding
        if (loggedAccount instanceof CreditAccount) {
            ((CreditAccount) loggedAccount).deposit(amount);
            System.out.println(" Payment of $" + amount + " has been processed successfully.");
            System.out.println(" Your new balance is $" + loggedAccount.getBalance() + ".");
        } else {
            System.out.println(" This account is not eligible for credit payments.");
        }
    }

    // Handle credit recompense or loan reduction
    public void creditRecompenseProcess(double amount) {
        if (!isLoggedIn()) {
            System.out.println(" You're not logged in. Please log in to apply a recompense.");
            return;
        }

        // Validate that it's a Credit Account
        if (loggedAccount instanceof CreditAccount) {
            String result = ((CreditAccount) loggedAccount).applyCompensation(amount);
            System.out.println(result);
        } else {
            System.out.println(" This account is not set up for recompense transactions.");
        }
    }

    // Get the logged Credit Account
    public CreditAccount getLoggedAccount() {
        if (!isLoggedIn()) {
            System.out.println(" No account is currently logged in. Please log in first.");
            return null;
        }

        // Check if the logged-in account is a Credit Account
        if (loggedAccount instanceof CreditAccount) {
            return (CreditAccount) loggedAccount;
        } else {
            System.out.println(" The currently logged-in account is not a Credit Account.");
            return null;
        }
    }
}
