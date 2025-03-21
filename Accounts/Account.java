package Accounts;

public abstract class Account {
    private String pin; // Add a field for the PIN


    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(String accountNumber, String accountHolderName, double initialDeposit, String pin) {
        this.pin = pin; // Initialize the PIN

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }
    }


    public void setBalance(double balance) {
        this.balance = balance; // Set the new balance
    }

    public String getPin() { // Add a getter for the PIN
        return pin;
    }

    public abstract String getAccountType();



    public abstract boolean validatePassword(String password);


    public void debit(double amount) {
        balance -= amount;
    }


    @Override
    public String toString() {
        return "Account No: " + accountNumber + " | Holder: " + accountHolderName + " | Balance: $" + balance;
    }
}
