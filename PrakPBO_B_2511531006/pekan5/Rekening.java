package pekan5;

import java.util.ArrayList;

public class Rekening {

    private String nomorRekening;
    private String namaPemilik;
    protected double saldo;
    private String pin;

    protected ArrayList<Transaksi> riwayatTransaksi =new ArrayList<Transaksi>();

    public Rekening(String nomor, String nama, double saldoAwal, String pin) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        this.pin = pin;

        System.out.println("Rekening atas nama " + namaPemilik+ " berhasil dibuat dengan saldo Rp" + saldo);
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;

            Transaksi transaksiBaru = new Transaksi("TRX-S-" + (riwayatTransaksi.size() + 1),"Kredit",nominal);

            riwayatTransaksi.add(transaksiBaru);

            System.out.println("Setor tunai Rp" + nominal+ " berhasil. Saldo saat ini: Rp" + saldo);
        } else {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!"
            );
        }
    }


    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
            return false;

        } else if (nominal < 10000) {
            System.out.println("Transaksi gagal: Minimal nominal penarikan Rp10000.");
            return false;

        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp"+ saldo);
            return false;

        } else {
            saldo -= nominal;

            String idTrx = "TRX-T-" + System.currentTimeMillis();
            Transaksi transaksiBaru = new Transaksi(idTrx,"Debit",nominal);

            riwayatTransaksi.add(transaksiBaru);

            System.out.println("Tarik tunai Rp" + nominal+ " berhasil. Sisa saldo: Rp" + saldo);

            return true;
        }
    }


    public void transfer(Rekening tujuan, double nominal, String pinInput) {
        if (!otentikasi(pinInput)) {
            System.out.println("Transfer gagal: PIN yang Anda masukkan salah!");
            return;
        }

        if (tujuan == null) {
            System.out.println("Transfer gagal: Rekening tujuan tidak ditemukan.");
            return;
        }

        if (this == tujuan) {
            System.out.println("Transfer gagal: Tidak dapat transfer ke rekening sendiri.");
            return;
        }

         if (this.tarikTunai(nominal)) {
            tujuan.setorTunai(nominal);

            System.out.println("Transfer berhasil ke rekening "+ tujuan.getNomorRekening() + ".");
        } else {
            System.out.println("Transfer gagal.");
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