package shop.payment;

public interface InstallmentPayment extends PaymentMethod {

    int MAX_MONTHS = 12;

    default long monthlyInstallment(long amount, int months) {
        if (months <= 0 || months > MAX_MONTHS) {
            throw new IllegalArgumentException("Invalid installment months");
        }

        return (amount + months - 1) / months;
    }
}