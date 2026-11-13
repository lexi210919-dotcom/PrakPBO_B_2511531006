package shop.shipping;

public class ExpressShipping extends ShippingService {
    public ExpressShipping() {
        super("Ekspres");
    }

    @Override
    protected long ratePerKg() {
        return 18_000;
    }

    @Override
    public long minimumOrder() {
        return 20_000L;
    }
}
