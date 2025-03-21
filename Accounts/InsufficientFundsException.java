package Accounts;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "InsufficientFundsException: " + getMessage();
    }
}
