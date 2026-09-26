package pekan3;

public class Transaksi {

    private String idTransaksi;
    private String jenis;
    private double nominal;

    public Transaksi(String idTransaksi, String jenis, double nominal) {
        this.idTransaksi = idTransaksi;
        this.jenis = jenis;
        this.nominal = nominal;
    }

    public String getIdTransaksi() {return idTransaksi;  }
    public String getJenis() {return jenis; }
    public double getNominal() {return nominal; }

    public void cetakDetail() {
        System.out.println("ID Transaksi : " + idTransaksi+ " | Jenis : " + jenis+ " | Nominal : Rp" + nominal);
    }
}

