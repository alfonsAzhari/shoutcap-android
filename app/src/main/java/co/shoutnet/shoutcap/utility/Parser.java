package co.shoutnet.shoutcap.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.model.ModelCapModel;
import co.shoutnet.shoutcap.model.ModelCart;
import co.shoutnet.shoutcap.model.ModelColor;
import co.shoutnet.shoutcap.model.ModelOnlyResult;
import co.shoutnet.shoutcap.model.ModelProvince;
import co.shoutnet.shoutcap.model.ModelQty;
import co.shoutnet.shoutcap.model.ModelRack;
import co.shoutnet.shoutcap.model.ModelResponseCheckout;
import co.shoutnet.shoutcap.model.ModelVoucher;

/**
 * Created by CodeLabs on 09/11/2015.
 */
public class Parser {

    private static Gson gson;

    public static ArrayList<ModelColor> getColorFromJson(String json) throws FileNotFoundException {
        gson = new Gson();

        Type listType = new TypeToken<List<ModelColor>>() {
        }.getType();

        return gson.fromJson(json, listType);
    }

    public static ArrayList<ModelCapModel> getModelFromJson(String json) throws FileNotFoundException {
        gson = new Gson();

        Type listType = new TypeToken<List<ModelCapModel>>() {
        }.getType();

        return gson.fromJson(json, listType);
    }

    public static ModelProvince getDataAddrs(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelProvince.class);
    }

    public static ModelCart getCartResponse(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelCart.class);
    }

    public static ModelRack getRackResponse(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelRack.class);
    }

    public static String getJsonCart(List<ModelQty> obj) throws IOException {
        gson = new Gson();
        return gson.toJson(obj);
    }

    public static ModelOnlyResult getResult(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelOnlyResult.class);
    }

    public static String getJsonVoucher(List<ModelVoucher> obj) throws IOException {
        gson = new Gson();
        return gson.toJson(obj);
    }

    public static ModelResponseCheckout getResultCheckout(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelResponseCheckout.class);
    }
}
