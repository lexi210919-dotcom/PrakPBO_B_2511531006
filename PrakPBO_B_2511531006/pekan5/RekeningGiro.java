package pekan5;

public class RekeningGiro extends Rekening {

    private double batasOverdraft;

    public RekeningGiro(String nomor,String nama,double saldoAwal,String pin,double batasOverdraft) {

        super(nomor, nama, saldoAwal, pin);
        this.batasOverdraft = batasOverdraft;
    }

    public double getBatasOverdraft() {
        return batasOverdraft;
    }

    @Override
    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik Giro harus lebih dari 0!");
            return false;

        } else if (nominal < 10000) {
            System.out.println("Transaksi gagal: Minimal nominal penarikan Rp10000.");
            return false;

        } else if (nominal > saldo + batasOverdraft) {
            System.out.println("Transaksi Gagal: Melebihi batas overdraft!");

            System.out.println("Maksimal penarikan: Rp" + (saldo + batasOverdraft));

            return false;

        } else {
            saldo -= nominal;

            String idTrx = "TRX-G-" + System.currentTimeMillis();
            Transaksi transaksiBaru = new Transaksi(idTrx,"Debit Giro",nominal);

            riwayatTransaksi.add(transaksiBaru);

            System.out.println("Tarik tunai Giro Rp" + nominal+ " berhasil. Saldo saat ini: Rp" + saldo);

            return true;
        }
    }
}