package co.shoutnet.shoutcap.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.model.ModelCapModel;
import co.shoutnet.shoutcap.model.ModelCaraBayar;
import co.shoutnet.shoutcap.model.ModelColor;
import co.shoutnet.shoutcap.model.ModelHistoryReward;
import co.shoutnet.shoutcap.model.ModelInbox;
import co.shoutnet.shoutcap.model.ModelInboxDetail;
import co.shoutnet.shoutcap.model.ModelInvoice;
import co.shoutnet.shoutcap.model.ModelMessage;
import co.shoutnet.shoutcap.model.ModelOrderHistory;
import co.shoutnet.shoutcap.model.ModelPaymentConfirmation;
import co.shoutnet.shoutcap.model.ModelSignIn;
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

    public static ModelSignIn getReturnSignIn(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelSignIn.class);
    }

    public static ModelInbox getInbox(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelInbox.class);
    }

    public static ModelInboxDetail getInboxDetail(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelInboxDetail.class);
    }

    public static ModelHistoryReward getRewardHistory(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelHistoryReward.class);
    }

    public static ModelVoucher getVoucher(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelVoucher.class);
    }

    public static ModelOrderHistory getOrderHistory(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelOrderHistory.class);
    }

    public static ModelInvoice getInvoice(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelInvoice.class);
    }

    public static ModelCaraBayar getCaraBayar(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelCaraBayar.class);
    }

    public static ModelMessage getMessage(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelMessage.class);
    }
}