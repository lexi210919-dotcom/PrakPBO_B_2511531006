package pekan2;

import java.util.ArrayList;

public class Rekening {
    String nomorRekening;
    String namaPemilik;
    double saldo;

    ArrayList<Transaksi> riwayatTransaksi =new ArrayList<Transaksi>();

    public Rekening(String nomor, String nama, double saldoAwal) {
        nomorRekening = nomor;
        namaPemilik = nama;
        saldo = saldoAwal;

        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
    }

    public void setorTunai(double nominal) {

        if (nominal > 0) {
            saldo += nominal;
            
           Transaksi transaksiBaru =new Transaksi("TRX-S-" + (riwayatTransaksi.size() + 1),"Kredit",nominal);
           riwayatTransaksi.add(transaksiBaru);
           System.out.println("Setor tunai Rp" + nominal+" berhasil. Saldo saat ini: Rp" + saldo);

        } else {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!");
        }
    }

    public void tarikTunai(double nominal) {

        if (nominal > saldo) {

            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp"+ saldo);

        } else if (nominal < 10000) {

            System.out.println("Transaksi Gagal : Minimal nominal penarikan 10.000");

        } else {

            saldo -= nominal;

            Transaksi transaksiBaru =new Transaksi("TRX-T-" + (riwayatTransaksi.size() + 1),"Debit",nominal);
            riwayatTransaksi.add(transaksiBaru);
            System.out.println("Tarik tunai Rp" + nominal+" berhasil. Saldo saat ini: Rp" + saldo);
        }
    }

    public void cekInformasi() {
        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("---------------------");
    }

    public void cetakMutasi() {

        System.out.println("==MUTASI REKENING==");

        if (riwayatTransaksi.isEmpty()) {

            System.out.println("Belum ada transaksi pada rekening ini");

        } else {

            for (Transaksi transaksi : riwayatTransaksi) {

                transaksi.cetakDetail();
            }
        }

        System.out.println("-----------------------");
    }
}
