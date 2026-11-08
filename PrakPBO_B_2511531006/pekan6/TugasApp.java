package shop.app;

import java.util.List;

import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {

    public static void main(String[] args) {
        System.out.println("Tugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")");

        // 1. Keranjang contoh dan kurir Ekspres
        Product coffee = new PhysicalProduct("P001", "Kopi Arabika Gayo 250g", 85_000, 250);
        Product ebook = new DigitalProduct("D001", "E-book Resep Kue Nusantara", 50_000,
                "https://example.com/ebook");
        List<Product> cart = List.of(coffee, ebook);

        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();

        // 2. Tiga metode pembayaran
        EWalletPayment eWallet = new EWalletPayment("DANA", 1_000_000);
        PaymentMethod transfer = new BankTransferPayment("Bank BRI");
        CreditCardPayment creditCard = new CreditCardPayment("Lexi", 1_000_000);

        // 3. Grand total tiap metode pembayaran
        System.out.println("\n== T1: Total dengan biaya admin (Ekspres) ==");
        long total = checkout.total(cart, express);
        for (PaymentMethod payment : List.of(eWallet, transfer, creditCard)) {
            System.out.println(payment.getName() + " : Rp"
                    + checkout.grandTotal(cart, express, payment)
                    + " (admin Rp" + payment.adminFee(total) + ")");
        }

        // 4. Kebijakan (policy) kartu kredit dan e-wallet
        System.out.println("Kebijakan kartu kredit : " + creditCard.policy());
        System.out.println("Kebijakan e-wallet : " + eWallet.policy());

        // 5. Daftarkan kanal notifikasi
        NotificationService notificationService = new NotificationService();
        notificationService.register(new EmailChannel());
        notificationService.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        notificationService.register(new WhatsAppChannel());

        // Kanal log konsol berupa anonymous class
        notificationService.register(new NotificationChannel() {
            @Override
            public String channelName() {
                return "LOG";
            }

            @Override
            public boolean send(CustomerContact contact, String message) {
                if (contact == null || message == null || message.isBlank()) {
                    return false;
                }
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        });

        // 6. Pesan panjang (lebih dari 200 karakter)
        String longMessage = "Terima kasih telah berbelanja di Toko UMKM. "
                + "Pesanan Anda sedang diproses. "
                + "Kami akan memberikan informasi terbaru "
                + "mengenai status pesanan dan pengiriman. "
                + "Semoga Anda puas dengan pelayanan kami. "
                + "Terima kasih atas kepercayaan Anda.";

        // 7. Dua kontak pelanggan
        CustomerContact first = new CustomerContact(
                "Pelanggan Pertama", "pelanggan@gmail.com", "081234567890");
        CustomerContact second = new CustomerContact(
                "Pelanggan Kedua", null, "+6281234567890");

        // 8. Kirim notifikasi
        System.out.println("\n== T2: Notifikasi status pesanan ==");
        int sentToFirst = notificationService.broadcast(first, longMessage);
        System.out.println("Terkirim ke " + first.getName() + " lewat " + sentToFirst + " kanal");

        int sentToSecond = notificationService.broadcast(second, longMessage);
        System.out.println("Terkirim ke " + second.getName() + " lewat " + sentToSecond + " kanal");
    }
}