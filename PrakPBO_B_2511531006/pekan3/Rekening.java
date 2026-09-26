package pekan3;

import java.util.ArrayList;

public class Rekening {

    private String nomorRekening;
    private String namaPemilik;
    private double saldo;
    private String pin;

    private ArrayList<Transaksi> riwayatTransaksi =new ArrayList<Transaksi>();

    public Rekening(String nomor, String nama, double saldoAwal, String pin) {
        nomorRekening = nomor;
        namaPemilik = nama;
        saldo = saldoAwal;
        this.pin = pin;
        System.out.println("Rekening atas nama " + namaPemilik+ " berhasil dibuat dengan saldo Rp" + saldo);
    }

    public String getNomorRekening() {return nomorRekening;}
    public String getNamaPemilik() {return namaPemilik;}
    public double getSaldo() {return saldo;}

    public void setorTunai(double nominal) {

        if (nominal > 0) {

            saldo += nominal;
            Transaksi transaksiBaru = new Transaksi("TRX-S-" + (riwayatTransaksi.size() + 1),"Kredit",nominal);
            riwayatTransaksi.add(transaksiBaru);
            System.out.println("Setor tunai Rp" + nominal+ " berhasil. Saldo saat ini: Rp" + saldo);

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
            Transaksi transaksiBaru = new Transaksi("TRX-T-" + (riwayatTransaksi.size() + 1),"Debit",nominal);
            
            riwayatTransaksi.add(transaksiBaru);
            
            System.out.println("Tarik tunai Rp" + nominal+ " berhasil. Saldo saat ini: Rp" + saldo);
        }
    }

    public void cekInformasi() {
        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
    }

    public void cetakMutasi() {
        System.out.println("--- MUTASI REKENING ---");
        
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini");

        } else {
            for (Transaksi transaksi : riwayatTransaksi) {
                transaksi.cetakDetail();
            }
        }

        System.out.println();
    }

    public boolean otentikasi(String pinYangDiinput) {
        return pin.equals(pinYangDiinput);
    }
}