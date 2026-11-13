
package shop.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import shop.exception.MinimumOrderException;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.shipping.ShippingService;
import shop.shipping.Shippable;

public class CheckoutService {
    private final Inventory inventory;

    public CheckoutService(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory tidak boleh null");
        }
        this.inventory = inventory;
    }

    public long checkout(List<Product> items, ShippingService shipping,
            PaymentMethod payment)
            throws OutOfStockException, PaymentFailedException, MinimumOrderException {

        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Keranjang belanja kosong");
        }

        if (shipping == null || payment == null) {
            throw new IllegalArgumentException("Shipping dan payment tidak boleh null");
        }

        long total = 0;
        int totalWeight = 0;
        Map<String, Integer> quantities = new LinkedHashMap<>();

        for (Product product : items) {
            if (product == null) {
                throw new IllegalArgumentException("Produk tidak boleh null");
            }

            total += product.getFinalPrice();
            quantities.merge(product.getSku(), 1, Integer::sum);

            if (product instanceof Shippable) {
                Shippable item = (Shippable) product;
                totalWeight += item.getWeightGrams();
            }
        }


        if (total < shipping.minimumOrder()) {
            throw new MinimumOrderException("Minimum pesanan " + shipping.getName() + " Rp" + shipping.minimumOrder());
        }

        total += shipping.calculateCost(totalWeight);

        List<String> reserved = new ArrayList<>();

        try {
            for (Map.Entry<String, Integer> entry : quantities.entrySet()) {
                inventory.reserve(entry.getKey(), entry.getValue());
                reserved.add(entry.getKey());
            }
        } catch (OutOfStockException e) {
            for (String sku : reserved) {
                inventory.release(sku, quantities.get(sku));
            }
            throw e;
        }

        try {
            payment.pay(total);
        } catch (PaymentFailedException e) {
            for (Map.Entry<String, Integer> entry : quantities.entrySet()) {
                inventory.release(entry.getKey(), entry.getValue());
            }
            throw e;
        }

        return total;
    }
}




