package co.shoutnet.shoutcap.model;

/**
 * Created by Adam MB on 1/13/2016.
 */
public class ModelPaymentConfirmation {
    private String idorder;
    private String nama_pemesan;
    private String hp;
    private String email;
    private String tgl_bayar;
    private int id_cara_bayar;
    private String bank_asal;
    private String nama_rekening;
    private int jumlah;

    public ModelPaymentConfirmation(String idorder, String nama_pemesan, String hp, String email, String tgl_bayar, int id_cara_bayar, String bank_asal, String nama_rekening, int jumlah) {
        this.idorder = idorder;
        this.nama_pemesan = nama_pemesan;
        this.hp = hp;
        this.email = email;
        this.tgl_bayar = tgl_bayar;
        this.id_cara_bayar = id_cara_bayar;
        this.bank_asal = bank_asal;
        this.nama_rekening = nama_rekening;
        this.jumlah = jumlah;
    }

    public String getIdorder() {
        return idorder;
    }

    public void setIdorder(String idorder) {
        this.idorder = idorder;
    }

    public String getNama_pemesan() {
        return nama_pemesan;
    }

    public void setNama_pemesan(String nama_pemesan) {
        this.nama_pemesan = nama_pemesan;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTgl_bayar() {
        return tgl_bayar;
    }

    public void setTgl_bayar(String tgl_bayar) {
        this.tgl_bayar = tgl_bayar;
    }

    public int getId_cara_bayar() {
        return id_cara_bayar;
    }

    public void setId_cara_bayar(int id_cara_bayar) {
        this.id_cara_bayar = id_cara_bayar;
    }

    public String getBank_asal() {
        return bank_asal;
    }

    public void setBank_asal(String bank_asal) {
        this.bank_asal = bank_asal;
    }

    public String getNama_rekening() {
        return nama_rekening;
    }

    public void setNama_rekening(String nama_rekening) {
        this.nama_rekening = nama_rekening;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
