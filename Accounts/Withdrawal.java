package Accounts;

public interface Withdrawal {
    /**
     * Withdraw an amount of money from a given account.
     * @param amount Amount to be withdrawn.
     * @return Flag if transaction is successful or not.
     * @throws IllegalArgumentException if the amount is less than or equal to zero.
     */
    public boolean withdraw(double amount);

    /**
     * Withdraws an amount of money using a given medium.
     * @param amount Amount of money to be withdrawn from.
     */
    public boolean withdrawal(double amount);
}
