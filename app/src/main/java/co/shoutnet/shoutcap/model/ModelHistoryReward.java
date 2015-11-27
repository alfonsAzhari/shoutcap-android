package co.shoutnet.shoutcap.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CodeLabs on 26/11/2015.
 */
public class ModelHistoryReward {

    private String result;
    private ArrayList<Item> item;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }

    public class Item {

        private String no;
        private String history;
        private String date;
        @SerializedName("bobot_coin")
        private String bobotCoin;
        @SerializedName("jumlah_coin")
        private String jumlahCoin;
        @SerializedName("bobot_point")
        private String bobotPoint;
        @SerializedName("jumlah_point")
        private String jumlahPoint;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBobotCoin() {
            return bobotCoin;
        }

        public void setBobotCoin(String bobotCoin) {
            this.bobotCoin = bobotCoin;
        }

        public String getJumlahCoin() {
            return jumlahCoin;
        }

        public void setJumlahCoin(String jumlahCoin) {
            this.jumlahCoin = jumlahCoin;
        }

        public String getBobotPoint() {
            return bobotPoint;
        }

        public void setBobotPoint(String bobotPoint) {
            this.bobotPoint = bobotPoint;
        }

        public String getJumlahPoint() {
            return jumlahPoint;
        }

        public void setJumlahPoint(String jumlahPoint) {
            this.jumlahPoint = jumlahPoint;
        }
    }
}
