package Main;

import Bank.Bank;
import java.util.HashMap;
import java.util.Map;

public class BankManager {
    private Map<String, Bank> banks;

    public BankManager() {
        this.banks = new HashMap<>();
    }

    public boolean createBank(String bankName, String adminUsername, String passcode) {
        if (banks.containsKey(bankName)) {
            return true; // Bank already exists
        }
        Bank newBank = new Bank(bankName, passcode);
        banks.put(bankName, newBank);
        return true;
    }

    public boolean validateBank(String bankName, String adminUsername, String passcode) {
        Bank bank = banks.get(bankName);
        return bank != null && bank.validatePasscode(passcode);
    }

    public Bank getBank(String bankName) {
        return banks.get(bankName);
    }
}
