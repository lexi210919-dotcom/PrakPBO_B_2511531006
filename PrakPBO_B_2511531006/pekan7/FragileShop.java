
package review;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import shop.exception.CatalogLoadException;
import shop.exception.PaymentFailedException;
import shop.payment.PaymentMethod;

public class FragileShop {

    public static void payOrder(PaymentMethod payment, long amount)
            throws PaymentFailedException {
        payment.pay(amount);
    }

    public static String readCatalogHeader(Path path)
            throws CatalogLoadException {

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.readLine();

        } catch (IOException e) {
            throw new CatalogLoadException(
                    "Gagal membaca katalog: " + path, e
            );
        }
    }
}
