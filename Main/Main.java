package Main;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.InsufficientFundsException; // Importing SavingsAccount class
import Accounts.SavingsAccount;
import Accounts.Transaction;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static BankManager bankManager = new BankManager();

    private static AccountManager accountManager = new AccountManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("~~~ Welcome to the Banking System! ~~~");
            System.out.println("[1] Create a New Bank");
            System.out.println("[2] Login to a Bank");
            System.out.println("[3] Account Login");
            System.out.println("[4] Exit");
            System.out.print("Select an option (1-4): ");
    

            if (!input.hasNextInt()) {
                input.nextLine(); // Consume invalid input
                System.out.println("\nInvalid input. Select only from (1-4):    ");
                System.out.println(); // Prints a blank line
                continue;
            }

            int choice = input.nextInt();
            System.out.println(); // Prints a blank line
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createNewBank();
                    break;
                case 2:
                    loginToBank();
                    break;
                case 3:
                    accountLogin();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please try again.");
                    System.out.println(); // Prints a blank line
            }
        }
    }

    private static void createNewBank() {
        System.out.print("Enter Bank Name: ");
        String bankName = input.nextLine();
        System.out.print("Enter Admin Username: ");
        String adminUsername = input.nextLine();
        System.out.print("Enter Admin Password: ");
        String adminPassword = input.nextLine();
        System.out.println(); // Prints a blank line
        
        if (bankManager.createBank(bankName, adminUsername, adminPassword)) {
            System.out.println("Bank '" + bankName + "' created successfully!");
            System.out.println(); // Prints a blank line
        } else {
            System.out.println("Bank creation failed. Bank may already exist.");
            System.out.println(); // Prints a blank line
        }
    }

    private static void loginToBank() {
        System.out.print("Enter Bank Name: ");
        String bankName = input.nextLine();
        System.out.print("Enter Admin Username: ");
        String adminUsername = input.nextLine();
        System.out.print("Enter Password: ");
        String password = input.nextLine();
        System.out.println(); // Prints a blank line
        
        if (bankManager.validateBank(bankName, adminUsername, password)) {
            System.out.println("Login successful. Welcome to " + bankName + "!");
            System.out.println(); // Prints a blank line
            showBankMenu();
        } else {
            System.out.println("Login failed. Invalid bank name or password.");
            System.out.println(); // Prints a blank line
        }
    }

    private static void showBankMenu() {
        while (true) {
            System.out.println("~~~ Bank Menu ~~~");
            System.out.println("[1] View Accounts");
            System.out.println("[2] Create a New Account");
            System.out.println("[3] Logout");
            System.out.print("Select an Option: ");

            if (!input.hasNextInt()) {
                input.nextLine(); // Consume invalid input
                System.out.println(" Invalid input. Please try again.\n");
                continue;
            }
    
            int choice = input.nextInt();
            System.out.println(); // Prints a blank line
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAccounts();
                    break;
                case 2:
                    createNewAccount();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    System.out.println(); // Prints a blank line
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    System.out.println(); // Prints a blank line
            }
        }
    }

    private static void viewAccounts() {
        while (true) {
            System.out.println("~~~ View Accounts Menu ~~~");
            System.out.println("[1] View All Accounts");
            System.out.println("[2] View Savings Accounts");
            System.out.println("[3] View Credit Accounts");
            System.out.println("[4] Back to Bank Menu");
            System.out.print("Select an Option: ");
    
            if (!input.hasNextInt()) {
                input.nextLine();
                System.out.println(" Invalid input. Please try again.\n");
                continue;
            }
    
            int choice = input.nextInt();
            System.out.println(); // Prints a blank line
            input.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    viewAllAccounts();
                    break;
                case 2:
                    viewAccountsByType("Savings");
                    break;
                case 3:
                    viewAccountsByType("Credit");
                    break;
                case 4:
                    return;
                default:
                    System.out.println(" Invalid option. Please try again.\n");
            }
        }
    }
    private static void viewAllAccounts() {
        Map<String, Account> accounts = accountManager.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            System.out.println(); // Prints a blank line
        } else {
System.out.println("~~~ ALL ACCOUNTS ~~~");

            System.out.println("-----------------------------------");

            for (Account account : accounts.values()) {
                String accountType = account.getAccountType();
                if (accountType.equalsIgnoreCase("savings")) {
                    System.out.println("Account No: " + account.getAccountNumber() + " | Type: " + accountType + " | Balance: $" + account.getBalance());
                } else if (accountType.equalsIgnoreCase("credit")) {
                    System.out.println("Account No: " + account.getAccountNumber() + " | Type: " + accountType + " | Credit Limit: $" + account.getBalance() + " | Debt: $" + account.getBalance());
                }
            }
            System.out.println("-----------------------------------");
            System.out.println("Total Accounts: " + accounts.size());
            System.out.println(); // Prints a blank line
        }
    }

    private static void viewAccountsByType(String accountType) {
        Map<String, Account> accounts = accountManager.getAccountsByType(accountType);
        if (accounts.isEmpty()) {
            System.out.println("No " + accountType + " accounts found.");
            System.out.println(); // Prints a blank line
        } else {
            System.out.println(accountType.substring(0, 1).toUpperCase() + accountType.substring(1) + " Accounts:");
            for (Account account : accounts.values()) {
                System.out.println(account);
            }
        }
    }

    private static void createNewAccount() {
        while (true) {
            System.out.println("Select an account type:");
            System.out.println("[1] Create a Savings Account");
            System.out.println("[2] Create a Credit Account");
            System.out.println("[3] Back to Bank Menu");
            System.out.print("Select an Option: ");
    
            if (!input.hasNextInt()) {
                input.nextLine(); // Consume invalid input
                System.out.println(" Invalid input. Please try again.\n");
                continue;
            }
    
            int choice = input.nextInt();
            System.out.println(); // Prints a blank line
            input.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    createSavingsAccount();
                    break;
                case 2:
                    createCreditAccount();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(" Invalid option. Please try again.\n");
            }
        }
    }

    private static void createSavingsAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = input.nextLine().trim();
        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = input.nextLine().trim();
        System.out.print("Enter Initial Deposit Amount: ");
        String initialDeposit = input.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = input.nextLine().trim();
    
        // Check if the account number already exists
        if (accountManager.getAllAccounts().containsKey(accountNumber)) {
            System.out.println("Account creation failed. Account number already exists.\n");
            return;
        }
    
        // Validate and convert initialDeposit to integer
        double depositAmount;

        try {
            depositAmount = Double.parseDouble(initialDeposit);

            if (depositAmount <= 0) {
                System.out.println("Initial deposit must be a positive amount.\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid deposit amount. Please enter a valid number.\n");
            return;
        }
    
        // Create the savings account
        if (accountManager.createAccount(accountNumber, accountHolderName, "savings", depositAmount, password, "", 0.0)) {

            System.out.println("Savings account created successfully!\n");
        } else {
            System.out.println("Account creation failed. Please try again.\n");
        }
    }
    

    private static void createCreditAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = input.nextLine().trim();
        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = input.nextLine().trim();
        System.out.print("Enter Credit Limit Amount: ");
        String creditLimitInput = input.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = input.nextLine().trim();
    
        // Check if the account number already exists
        if (accountManager.getAllAccounts().containsKey(accountNumber)) {
            System.out.println("Account creation failed. Account number already exists.\n");
            return;
        }
    
        // Validate and convert credit limit to double
        double creditLimit;
        try {
            creditLimit = Double.parseDouble(creditLimitInput);
            if (creditLimit <= 0) {
                System.out.println("Credit limit must be a positive amount.\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid credit limit. Please enter a valid number.\n");
            return;
        }
    
        // Create the credit account
        if (accountManager.createAccount(accountNumber, accountHolderName, "credit", creditLimit, password, "", creditLimit)) {

            System.out.println("Credit account created successfully!\n");
        } else {
            System.out.println("Account creation failed. Please try again.\n");
        }
    }
    

    private static void accountLogin() { 
        System.out.print("Enter Account Number: ");
        String accountNumber = input.nextLine();
        System.out.print("Enter Account Password: ");
        String password = input.nextLine();

        if (accountManager.validateAccount(accountNumber, password)) {
            System.out.println("Login successful. Welcome, " + accountManager.getAccountHolderName(accountNumber) + "!");
            System.out.println(); // Prints a blank line

            // Display Bank Services Menu
            showBankServicesMenu();
        } else {
            System.out.println("Login failed. Incorrect account number or password."); 
            System.out.println("Please try again.");
            System.out.println(); // Prints a blank line
        }
    }

    private static void showBankServicesMenu() {
        while (true) {
            System.out.println("BANK SERVICES MENU");
            System.out.println("[1] Savings Account");
            System.out.println("[2] Credit Account");
            System.out.println("[3] Logout");
            System.out.print("Select an Option: ");
    
            if (!input.hasNextInt()) {
                input.nextLine(); // Consume invalid input
                System.out.println(" Invalid input. Please try again.\n");
                continue;
            }
    
            int choice = input.nextInt();
            System.out.println(); // Prints a blank line
            input.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    showSavingsAccountMenu();
                    break;
                case 2:
                    showCreditAccountMenu();
                    break;
                case 3:
                    System.out.println("Logging out...\n");
                    return;
                default:
                    System.out.println(" Invalid option. Please try again.\n");
            }
        }
    }

    private static void showSavingsAccountMenu() {
        while (true) {
            System.out.println("~~~ SAVINGS ACCOUNT ~~~");
            System.out.println("[1] Fund Transfer (Internal)");
            System.out.println("[2] Fund Transfer (External)");
            System.out.println("[3] Withdraw Funds");
            System.out.println("[4] Deposit Funds");
            System.out.println("[5] View Transaction History");
            System.out.println("[6] Return to Main Menu");
            System.out.print("Enter your choice: ");

            if (!input.hasNextInt()) {
                input.nextLine(); // Consume invalid input
                System.out.println(" Invalid input. Please try again.\n");
                continue;
            }
    
            int choice = input.nextInt();
            System.out.println(); // Prints a blank line
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Internal Fund Transfer
                    System.out.print("Enter Your Account Number: ");
                    String yourAccountNumber = input.nextLine(); // Define yourAccountNumber here

                    System.out.print("Enter Recipient Account Number: ");
                    String recipientAccountNumber = input.nextLine();
                    System.out.print("Enter Transfer Amount: ");
                    double transferAmount = input.nextDouble();
                    input.nextLine(); // Consume newline

                    Account yourAccount = accountManager.getAllAccounts().get(yourAccountNumber);
                    Account recipientAccount = accountManager.getAllAccounts().get(recipientAccountNumber);

                    if (yourAccount == null) {
                        System.out.println("[Error] Your account does not exist.");
                        System.out.println(); // Prints a blank line
                    } else if (recipientAccount == null) {
                        System.out.println("[Error] Recipient account \"" + recipientAccountNumber + "\" does not exist.");
                        System.out.println(); // Prints a blank line
                    } else if (yourAccount.getBalance() < transferAmount) {
                        System.out.println("[Error] Insufficient balance. Available: " + yourAccount.getBalance() + " Required: " + transferAmount);
                        System.out.println(); // Prints a blank line
                    } else {
                        accountManager.transferFunds(yourAccountNumber, recipientAccountNumber, transferAmount);

                        System.out.println("[Success Internal Fund Transfer] " + transferAmount + " transferred from " + yourAccountNumber + " to " + recipientAccountNumber + ".");
                        System.out.println("New Balance (" + yourAccountNumber + "): " + yourAccount.getBalance());
                        System.out.println("New Balance (" + recipientAccountNumber + "): " + recipientAccount.getBalance());
                        System.out.println(); // Prints a blank line
                    }
                    break;
                case 2:
                    // External Fund Transfer
                    System.out.print("Enter Your Account Number: ");
                    String yourExternalAccountNumber = input.nextLine();
                    System.out.print("Enter Recipient Account Number: ");
                    String externalRecipientAccountNumber = input.nextLine();
                    System.out.print("Enter Transfer Amount: ");
                    double externalTransferAmount = input.nextDouble();
                    System.out.print("Enter Processing Fee: ");
                    double processingFee = input.nextDouble();
                    input.nextLine(); // Consume newline

                    Account yourExternalAccount = accountManager.getAllAccounts().get(yourExternalAccountNumber);
                    Account externalRecipientAccount = accountManager.getAllAccounts().get(externalRecipientAccountNumber);

                    if (yourExternalAccount == null) {
                        System.out.println("[Error] Your account does not exist.");
                        System.out.println(); // Prints a blank line
                    } else if (externalRecipientAccount == null) {
                        System.out.println("[Error] Recipient account \"" + externalRecipientAccountNumber + "\" does not exist.");
                        System.out.println(); // Prints a blank line
                    } else if (yourExternalAccount.getBalance() < (externalTransferAmount + processingFee)) {
                        System.out.println("[Error] Insufficient funds. Available: " + yourExternalAccount.getBalance() + " Required (Transfer + Fee): " + (externalTransferAmount + processingFee));
                        System.out.println(); // Prints a blank line
                    } else {
                        // Logic for external transfer (e.g., deduct processing fee)
                        ((SavingsAccount) yourExternalAccount).transfer((SavingsAccount) externalRecipientAccount, externalTransferAmount);
                        try {
                            accountManager.withdraw(yourExternalAccountNumber, processingFee); // Deduct processing fee
                        } catch (InsufficientFundsException e) {
                            System.out.println("[Error] Insufficient funds for processing fee.");
                            System.out.println(); // Prints a blank line
                            return; // Exit the method if there are insufficient funds
                        }

                        System.out.println("[Success External Fund Transfer] " + externalTransferAmount + " transferred from " + yourExternalAccountNumber + " to " + externalRecipientAccountNumber + ".");
                        System.out.println("Processing Fee Deducted: " + processingFee);
                        System.out.println("New Balance (" + yourExternalAccountNumber + "): " + yourExternalAccount.getBalance());
                        System.out.println("New Balance (" + externalRecipientAccountNumber + "): " + externalRecipientAccount.getBalance());
                        System.out.println(); // Prints a blank line
                    }

                    break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    String accountNumber = input.nextLine();
                    System.out.print("Enter PIN: ");
                    String pin = input.nextLine();
                    System.out.print("Enter Withdrawal Amount: ");
                    double withdrawalAmount = input.nextDouble();
                    input.nextLine(); // Consume newline

                    // Validate PIN and perform withdrawal
                    if (accountManager.validatePin(accountNumber, pin)) {
                        try {
                            accountManager.withdraw(accountNumber, withdrawalAmount);
                            System.out.println("[Success] " + withdrawalAmount + " withdrawn from Account " + accountNumber + ".");
                            System.out.println("New Balance: " + accountManager.getBalance(accountNumber));
                            System.out.println(); // Prints a blank line
                        } catch (InsufficientFundsException e) {
                            System.out.println("[Error] Insufficient funds for withdrawal.");
                            System.out.println(); // Prints a blank line
                        }
                    } else {
                        System.out.println("[Error] Incorrect PIN. Withdrawal denied.");
                        System.out.println(); // Prints a blank line
                    }


                    break;
                case 4:
                    // Implement Deposit Funds logic here
                    System.out.print("Enter Account Number: ");
                    String depositAccountNumber = input.nextLine();
                    System.out.print("Enter Deposit Amount: ");
                    double depositAmount = input.nextDouble();
                    input.nextLine(); // Consume newline

                    // Validate deposit limit
                    if (depositAmount <= accountManager.getMaxDepositLimit()) {
                        accountManager.deposit(depositAccountNumber, depositAmount);
                        System.out.println("[Success] " + depositAmount + " deposited to " + depositAccountNumber + ".");
                        System.out.println("Updated Balance: " + accountManager.getBalance(depositAccountNumber) + "");
                        System.out.println(); // Prints a blank line
                    } else {
                        System.out.println("[Error] Deposit limit exceeded. Maximum allowed: " + accountManager.getMaxDepositLimit() + "");
                        System.out.println(); // Prints a blank line
                    }
                    break;
                case 5:
                    // Implement View Transaction History logic here
                    System.out.print("Enter Account Number: ");
                    accountNumber = input.nextLine();
                    SavingsAccount account = (SavingsAccount) accountManager.getAllAccounts().get(accountNumber);
                    
                    if (account != null) {
                        System.out.println("Transaction History for Account " + accountNumber + ":");
                        for (Transaction transaction : account.getTransactionHistory()) {
                            System.out.println(transaction);
                        }
                    } else {
                        System.out.println("[Error] Account not found.");
                    }
                    System.out.println(); // Prints a blank line
                    break;
                case 6:
                    return; // Go back to Bank Services Menu
                default:
                    System.out.println("Invalid option. Please try again.");
                    System.out.println(); // Prints a blank line
            }
        }
    }

    private static void showCreditAccountMenu() {
        while (true) {
            System.out.println("~~~ CREDIT ACCOUNT ~~~");
            System.out.println("[1] Credit Payment");
            System.out.println("[2] Credit Compensation");
            System.out.println("[3] View Transaction History");
            System.out.println("[4] Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            System.out.println(); // Prints a blank line
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Credit Account Number: ");
                    String creditAccountNumber = input.nextLine();
                    System.out.print("Enter Savings Account Number to Pay From: ");
                    String savingsAccountNumber = input.nextLine();
                    System.out.print("Enter Payment Amount: ");
                    double paymentAmount = input.nextDouble();
                    System.out.println(); // Prints a blank line
                    input.nextLine(); // Consume newline
                    

                    accountManager.processCreditPayment(creditAccountNumber, savingsAccountNumber, paymentAmount);
                    break;
                case 2:
                    System.out.print("Enter Credit Account Number: ");
                    String compensationAccountNumber = input.nextLine();
                    System.out.print("Enter Compensation Amount: ");
                    double compensationAmount = input.nextDouble();
                    System.out.println(); // Prints a blank line
                    input.nextLine(); // Consume newline

                    accountManager.processCreditCompensation(compensationAccountNumber, compensationAmount);
                    break;
                case 3:
                    // View Transaction History
                    System.out.print("Enter Credit Account Number: ");
                    creditAccountNumber = input.nextLine();
                    CreditAccount creditAccount = (CreditAccount) accountManager.getAllAccounts().get(creditAccountNumber);
                    
                    if (creditAccount != null) {
                        System.out.println("Transaction History for Credit Account " + creditAccountNumber + ":");
                        for (Transaction transaction : creditAccount.getTransactionHistory()) {
                            System.out.println(transaction);
                        }
                    } else {
                        System.out.println("[Error] Credit account not found.");
                    }
                    System.out.println(); // Prints a blank line
                    break;
                    
                case 4:
                    return; // Go back to Bank Services Menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
