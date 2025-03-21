package Accounts;

public class Recompense {
    private double amount;

    public Recompense(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Recompense Amount: " + amount;
    }
}
