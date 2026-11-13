
package shop.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shop.StudentInfo;
import shop.exception.InsufficientBalanceException;
import shop.exception.MinimumOrderException;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.service.Inventory;
import shop.shipping.RegularShipping;
import shop.shipping.ShippingService;

public class ShopApp {

    public static void main(String[] args) {
        List<Product> catalog = sampleCatalog();

        System.out.println("== Toko Online UMKM ==");

        for (int i = 0; i < catalog.size(); i++) {
            System.out.println((i + 1) + ". " + catalog.get(i).getInfo());
        }

        List<Product> cart = new ArrayList<>();

        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("Nomor produk (0 = selesai): ");

                int choice;
                try {
                    choice = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka.");
                    continue;
                }

                if (choice == 0) {
                    break;
                }

                if (choice < 1 || choice > catalog.size()) {
                    System.out.println("Nomor produk tidak ada.");
                    continue;
                }

                Product product = catalog.get(choice - 1);

                System.out.print("Jumlah: ");

                int quantity;
                try {
                    quantity = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka.");
                    continue;
                }

                if (quantity <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                    continue;
                }

                for (int i = 0; i < quantity; i++) {
                    cart.add(product);
                }

                System.out.println("+ " + quantity + " x " + product.getName());
            }
        }

        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong. Tidak ada pembayaran.");
            return;
        }

        Inventory inventory = new Inventory();

        for (Product product : catalog) {
            inventory.addStock(product.getSku(), 10);
        }

        CheckoutService checkout = new CheckoutService(inventory);
        ShippingService shipping = new RegularShipping();

        EWalletPayment wallet = new EWalletPayment(
                "DompetKu", StudentInfo.walletBalance());

        System.out.println(buy(checkout, cart, shipping, wallet));
        System.out.println("Sisa saldo: Rp" + wallet.getBalance());
    }

    public static String buy(
            CheckoutService service,
            List<Product> cart,
            ShippingService shipping,
            PaymentMethod payment) {

        if (cart == null || cart.isEmpty()) {
            return "Keranjang kosong. Tidak ada pembayaran.";
        }

        try {
            long total = service.checkout(cart, shipping, payment);
            return "[OK] Pembayaran berhasil Rp" + total;

        } catch (InsufficientBalanceException e) {
            return "[SALDO] Saldo kurang Rp" + e.getShortfall();

        } catch (PaymentFailedException e) {
            return "[BAYAR] Pembayaran gagal: " + e.getMessage();

        } catch (MinimumOrderException e) {
            return "[MINIMAL] " + e.getMessage();

        } catch (OutOfStockException e) {
            return "[STOK] Stok tidak mencukupi: " + e.getMessage();
        }
    }

    private static List<Product> sampleCatalog() {
        return List.of(
            new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85000, 300),
            new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120000, 400),
            new PhysicalProduct("TEH-004", "Teh Kayu Aro 100g", 35000, 150)
        );
    }
}

