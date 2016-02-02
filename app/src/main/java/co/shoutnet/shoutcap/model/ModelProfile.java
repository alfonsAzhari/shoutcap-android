package co.shoutnet.shoutcap.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CodeLabs on 26/11/2015.
 */
public class ModelProfile {

    private String result;
    private Item item;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public class Item {

        private String nama;
        private String email;
        private String hp;
        private String gender;
        private String alamat;
        private String kota;
        private String kecamatan;
        private String provinsi;
        @SerializedName("kode_pos")
        private String kodePos;
        @SerializedName("tgl_lahir")
        private String tglLahir;
        private ArrayList<String> minat;
        @SerializedName("status_kerja")
        private String statusKerja;

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHp() {
            return hp;
        }

        public void setHp(String hp) {
            this.hp = hp;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getKota() {
            return kota;
        }

        public void setKota(String kota) {
            this.kota = kota;
        }

        public String getKecamatan() {
            return kecamatan;
        }

        public void setKecamatan(String kecamatan) {
            this.kecamatan = kecamatan;
        }

        public String getProvinsi() {
            return provinsi;
        }

        public void setProvinsi(String provinsi) {
            this.provinsi = provinsi;
        }

        public String getKodePos() {
            return kodePos;
        }

        public void setKodePos(String kodePos) {
            this.kodePos = kodePos;
        }

        public String getTglLahir() {
            return tglLahir;
        }

        public void setTglLahir(String tglLahir) {
            this.tglLahir = tglLahir;
        }

        public ArrayList<String> getMinat() {
            return minat;
        }

        public void setMinat(ArrayList<String> minat) {
            this.minat = minat;
        }

        public String getStatusKerja() {
            return statusKerja;
        }

        public void setStatusKerja(String statusKerja) {
            this.statusKerja = statusKerja;
        }
    }
}
