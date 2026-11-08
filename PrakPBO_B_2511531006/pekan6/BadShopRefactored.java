package review.fixed;

import java.util.ArrayList;
import java.util.List;

// Interface khusus pembayaran
interface Payable {
    boolean pay(long amount);
}

// Interface khusus refund
interface Refundable {
    boolean refund(long amount);
}

// Interface khusus pengiriman
interface Shippable {
    long ship(int weightGrams);
}

// Voucher hanya membutuhkan kemampuan pembayaran
class GiftVoucher implements Payable {

    private double balance = 100000.0;

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public String printInvoice() {
        return "sisa Rp" + (long) balance;
    }
}

// Abstract class dengan perhitungan khusus setiap kurir
abstract class Courier {

    public abstract long calculateCost(int kilograms);
}

class JneCourier extends Courier {

    @Override
    public long calculateCost(int kilograms) {
        return kilograms * 10000L;
    }
}

class ExpressCourier extends Courier {

    @Override
    public long calculateCost(int kilograms) {
        return kilograms * 18000L;
    }
}

class SameDayCourier extends Courier {

    @Override
    public long calculateCost(int kilograms) {
        return kilograms * 30000L;
    }
}

// Cart dengan static nested class Item
class Cart {

    private final List<Item> items = new ArrayList<>();

    static class Item {

        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }
    }

    void add(Item item) {
        items.add(item);
    }

    long total() {
        long total = 0;

        for (Item item : items) {
            total += item.subtotal();
        }

        return total;
    }
}

// Address dibuat immutable
final class Address {

    private final String street;
    private final String city;

    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {

    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

// Interface diskon menggunakan polymorphism
interface Discount {

    long apply(long price);
}

class MemberDiscount implements Discount {

    @Override
    public long apply(long price) {
        return price - price * 5 / 100;
    }
}

class FlashSaleDiscount implements Discount {

    @Override
    public long apply(long price) {
        return price - price * 30 / 100;
    }
}

// Class utama
public class BadShopRefactored {

    public static void main(String[] args) {

        // 1. Menghitung total cart
        Cart cart = new Cart();

        cart.add(new Cart.Item("Kopi Gayo", 85000, 2));

        System.out.println("Total cart: " + cart.total());

        // 2. Menghitung ongkir Express
        Courier courier = new ExpressCourier();

        System.out.println("Ongkir Ekspres 2 kg: "
                + courier.calculateCost(2));

        // 3. Menguji alamat pesanan
        Address home1 = new Address(
                "Jl. Sudirman 10", "Padang");

        Address home2 = new Address(
                "Jl. Sudirman 10", "Bukittinggi");

        SimpleOrder first = new SimpleOrder("ORD-1", home1);
        SimpleOrder second = new SimpleOrder("ORD-2", home2);

        System.out.println(first);
        System.out.println(second);

        // 4. Menguji diskon
        Discount flashSale = new FlashSaleDiscount();

        System.out.println("Harga flash sale: "
                + flashSale.apply(100000));

        // 5. Menguji pembayaran voucher
        Payable voucher = new GiftVoucher();

        boolean success = voucher.pay(30000);

        GiftVoucher giftVoucher = (GiftVoucher) voucher;

        System.out.println(success + " | "
                + giftVoucher.printInvoice());
    }
}
