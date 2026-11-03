package shop.payment;

public class EWalletPayment implements RefundablePayment {

    private final String name;
    private long balance;

    public EWalletPayment(String name, long balance) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance must not be negative");
        }

        this.name = name;
        this.balance = balance;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    @Override
    public boolean refund(long amount) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }
}