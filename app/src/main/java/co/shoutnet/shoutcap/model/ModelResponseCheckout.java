package co.shoutnet.shoutcap.model;

/**
 * Created by Henra SN on 12/14/2015.
 */
public class ModelResponseCheckout {
    private String result;
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public class Item {
        private String id_checkout;
        private String disc_qty_persen;
        private String disc_qty_idr;
        private String disc_voucher_persen;
        private String disc_voucher_idr;
        private String ongkos_kirim;
        private String total;

        public String getDisc_qty_idr() {
            return disc_qty_idr;
        }

        public void setDisc_qty_idr(String disc_qty_idr) {
            this.disc_qty_idr = disc_qty_idr;
        }

        public String getDisc_qty_persen() {
            return disc_qty_persen;
        }

        public void setDisc_qty_persen(String disc_qty_persen) {
            this.disc_qty_persen = disc_qty_persen;
        }

        public String getDisc_voucher_idr() {
            return disc_voucher_idr;
        }

        public void setDisc_voucher_idr(String disc_voucher_idr) {
            this.disc_voucher_idr = disc_voucher_idr;
        }

        public String getDisc_voucher_persen() {
            return disc_voucher_persen;
        }

        public void setDisc_voucher_persen(String disc_voucher_persen) {
            this.disc_voucher_persen = disc_voucher_persen;
        }

        public String getId_checkout() {
            return id_checkout;
        }

        public void setId_checkout(String id_checkout) {
            this.id_checkout = id_checkout;
        }

        public String getOngkos_kirim() {
            return ongkos_kirim;
        }

        public void setOngkos_kirim(String ongkos_kirim) {
            this.ongkos_kirim = ongkos_kirim;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
