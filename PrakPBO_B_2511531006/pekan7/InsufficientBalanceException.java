package shop.exception;

public class InsufficientBalanceException extends PaymentFailedException {

    private static final long serialVersionUID = 1L;

    private final long required;
    private final long available;
    private final long shortfall;

    public InsufficientBalanceException(long required, long available) {
        super("Saldo tidak mencukupi");
        this.required = required;
        this.available = available;
        this.shortfall = required - available;
    }

    public long getRequired() {
        return required;
    }

    public long getAvailable() {
        return available;
    }

    public long getShortfall() {
        return shortfall;
    }
}
