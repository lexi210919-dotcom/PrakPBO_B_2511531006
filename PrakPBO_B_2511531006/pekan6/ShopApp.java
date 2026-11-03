package shop.app;

import java.util.List;

import shop.model.Address;
import shop.model.DigitalProduct;
import shop.model.Order;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.RegularShipping;

public class ShopApp {

    public static void main(String[] args) {

        Product coffee = new PhysicalProduct(
                "P001", "Kopi Arabika Gayo 250g", 85_000, 250);

        Product ebook = new DigitalProduct(
                "D001", "E-book Resep Kue Nusantara", 50_000,
                "https://example.com/ebook");

        CheckoutService checkout = new CheckoutService();
        RegularShipping regular = new RegularShipping();

        Address address = new Address(
                "Jl. Moh Hatta No. 10", "Padang", "25121");

        Order order = new Order("ORD-0001", address);
        order.addLine(coffee, 2);
        order.addLine(ebook, 1);

        Order.Receipt receipt = order.new Receipt();
        System.out.println(receipt.toText());

        PaymentMethod voucher = new PaymentMethod() {
            private long remaining = 50_000;

            @Override
            public String getName() {
                return "Voucher Promo Hari Ini";
            }

            @Override
            public boolean pay(long amount) {
                if (amount <= 0 || amount > remaining) {
                    return false;
                }

                remaining -= amount;
                return true;
            }
        };

        System.out.println("\n" + voucher.getName()
                + " untuk e-book berhasil? "
                + checkout.checkout(List.of(ebook), regular, voucher));
    }
}