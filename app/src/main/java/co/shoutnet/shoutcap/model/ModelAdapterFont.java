package co.shoutnet.shoutcap.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Codelabs on 8/20/2015.
 */
public class ModelAdapterFont {

    @SerializedName("result")
    String result;

    @SerializedName("item")
    ArrayList<Item> item;

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

        @SerializedName("font")
        String font;

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }
    }
}
