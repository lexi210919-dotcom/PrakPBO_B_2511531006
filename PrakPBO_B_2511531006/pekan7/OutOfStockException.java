package shop.exception;

public class OutOfStockException extends ShopException {

    private static final long serialVersionUID = 1L;

    private final String sku;
    private final int requested;
    private final int available;
    private final int shortfall;

    public OutOfStockException(String sku, int requested, int available) {
        super("Stok tidak mencukupi untuk produk " + sku);
        this.sku = sku;
        this.requested = requested;
        this.available = available;
        this.shortfall = requested - available;
    }

    public String getSku() {
        return sku;
    }

    public int getRequested() {
        return requested;
    }

    public int getAvailable() {
        return available;
    }

    public int getShortfall() {
        return shortfall;
    }
}
