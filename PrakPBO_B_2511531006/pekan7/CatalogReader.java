
package shop.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shop.exception.CatalogLoadException;
import shop.model.PhysicalProduct;
import shop.model.Product;

public class CatalogReader {

    private static final Logger LOGGER = Logger.getLogger("shop");

    private int skippedCount = 0;

    public List<Product> load(Path path) throws CatalogLoadException {
        List<Product> products = new ArrayList<>();
        skippedCount = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(";", -1);

                    if (data.length != 4) {
                        throw new IllegalArgumentException("Kolom tidak lengkap");
                    }

                    String sku = data[0].trim();
                    String name = data[1].trim();
                    long price = Long.parseLong(data[2].trim());
                    int weight = Integer.parseInt(data[3].trim());

                    if (price < 0 || weight <= 0) {
                        throw new IllegalArgumentException("Harga atau berat tidak valid");
                    }

                    Product product = new PhysicalProduct(
                            sku, name, price, weight
                    );

                    products.add(product);

                } catch (IllegalArgumentException e) {
                    skippedCount++;
                    LOGGER.warning("Baris katalog dilewati: " + line);
                }
            }

        } catch (IOException e) {
            throw new CatalogLoadException(
                    "Gagal membaca katalog: " + path, e
            );
        }

        return products;
    }

    public int getSkippedCount() {
        return skippedCount;
    }
}

