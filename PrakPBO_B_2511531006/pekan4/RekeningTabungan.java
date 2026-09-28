package pekan4;

public class RekeningTabungan extends Rekening {

    private double sukuBunga;

    public RekeningTabungan(
       String nomor,
       String nama,
       double saldoAwal,
       String pin,
       double sukuBunga) {

        super(nomor, nama, saldoAwal, pin);
        this.sukuBunga = sukuBunga;
    }

    public double getSukuBunga() {return sukuBunga;}

    public void tambahBungaAkhirBulan() {
        double bunga = saldo * sukuBunga / 100;

        saldo += bunga;

        System.out.println("Bunga akhir bulan sebesar Rp" + bunga +" telah ditambahkan.");
        System.out.println("Saldo setelah bunga: Rp" + saldo);
    }
}