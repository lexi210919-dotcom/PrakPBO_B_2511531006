package shop.exception;

public class MinimumOrderException extends ShopException {

    private static final long serialVersionUID = 1L;

    public MinimumOrderException(String message) {
        super(message);
    }
}
