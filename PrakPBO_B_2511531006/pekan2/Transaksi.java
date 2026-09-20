package pekan2;

public class Transaksi {

    String idTransaksi;
    String jenis;
    double nominal;

    public Transaksi(String idTransaksi, String jenis, double nominal) {
        this.idTransaksi = idTransaksi;
        this.jenis = jenis;
        this.nominal = nominal;
    }

    public void cetakDetail() {
        System.out.println("ID Transaksi : " + idTransaksi+ " | Jenis : " + jenis + " | Nominal : Rp" + nominal);
    }
}
