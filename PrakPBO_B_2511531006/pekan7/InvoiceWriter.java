package shop.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import shop.model.Product;

public class InvoiceWriter {

    public InvoiceWriter() {
    }

    public void write(Path file, List<Product> products, long total)
            throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Product product : products) {
                writer.write(product.getName() + " - Rp" + product.getFinalPrice());
                writer.newLine();
            }

            writer.write("TOTAL Rp" + total);
        }
    }
}
