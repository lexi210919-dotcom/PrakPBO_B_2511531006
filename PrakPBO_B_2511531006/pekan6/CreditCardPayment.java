package shop.payment;

public class CreditCardPayment implements RefundablePayment, InstallmentPayment {

    private final String cardHolder;
    private long limit;
    private long used;

    public CreditCardPayment(String cardHolder, long limit) {
        if (cardHolder == null || cardHolder.isBlank()) {
            throw new IllegalArgumentException("Card holder must not be blank");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("Limit must not be negative");
        }

        this.cardHolder = cardHolder;
        this.limit = limit;
    }

    @Override
    public String getName() {
        return "Kartu Kredit (" + cardHolder + ")";
    }

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > limit - used) {
            return false;
        }

        used += amount;
        return true;
    }

    @Override
    public boolean refund(long amount) {
        if (amount <= 0 || amount > used) {
            return false;
        }

        used -= amount;
        return true;
    }
}