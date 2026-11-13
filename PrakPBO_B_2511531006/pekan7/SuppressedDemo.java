package shop.demo;

import java.io.IOException;

public class SuppressedDemo {

    static class DemoResource implements AutoCloseable {
        @Override
        public void close() throws IOException {
            throw new IOException("Gagal menutup resource");
        }
    }

    public static void main(String[] args) {
        try (DemoResource resource = new DemoResource()) {
            throw new IllegalStateException("Exception utama");
        } catch (Exception e) {
            System.out.println("Exception utama: " + e.getMessage());

            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("Suppressed: " + suppressed.getMessage());
            }
        }
    }
}
