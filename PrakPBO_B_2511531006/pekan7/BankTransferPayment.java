
package shop.payment;

import shop.exception.PaymentFailedException;

public class BankTransferPayment implements PaymentMethod {
    private final String bankName;
    private boolean online = true;

    public BankTransferPayment(String bankName) {
        if (bankName == null || bankName.isBlank()) {
            throw new IllegalArgumentException("Bank name must not be blank");
        }
        this.bankName = bankName;
    }

    @Override
    public String getName() {
        return "Transfer " + bankName;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public void pay(long amount) throws PaymentFailedException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (!online) {
            throw new PaymentFailedException("Bank sedang offline");
        }
    }
}

