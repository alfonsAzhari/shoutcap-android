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
import co.shoutnet.shoutcap.model.ModelCart;
import co.shoutnet.shoutcap.model.ModelColor;
import co.shoutnet.shoutcap.model.ModelHistoryReward;
import co.shoutnet.shoutcap.model.ModelInbox;
import co.shoutnet.shoutcap.model.ModelInboxDetail;
import co.shoutnet.shoutcap.model.ModelInvoice;
import co.shoutnet.shoutcap.model.ModelMessage;
import co.shoutnet.shoutcap.model.ModelOnlyResult;
import co.shoutnet.shoutcap.model.ModelOrderHistory;
import co.shoutnet.shoutcap.model.ModelProvince;
import co.shoutnet.shoutcap.model.ModelQty;
import co.shoutnet.shoutcap.model.ModelRack;
import co.shoutnet.shoutcap.model.ModelResponseCheckout;
import co.shoutnet.shoutcap.model.ModelProfile;
import co.shoutnet.shoutcap.model.ModelSignIn;
import co.shoutnet.shoutcap.model.ModelSyncRack;
import co.shoutnet.shoutcap.model.ModelVoucher;
import co.shoutnet.shoutcap.model.ModelVoucherCart;
import co.shoutnet.shoutcap.model.ModelRegister;
import co.shoutnet.shoutcap.model.ModelRegisterError;

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

    public static ModelProfile getProfile(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelProfile.class);
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

    public static String getJsonVoucher(List<ModelVoucherCart> obj) throws IOException {
        gson = new Gson();
        return gson.toJson(obj);
    }

    public static ModelResponseCheckout getResultCheckout(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelResponseCheckout.class);
    }

    public static ModelSyncRack getSyncRack(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelSyncRack.class);
    }

    public static ModelRegister getRegister(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelRegister.class);
    }

    public static ModelRegisterError getRegisterError(String json) throws IOException {
        gson = new Gson();
        return gson.fromJson(json, ModelRegisterError.class);
    }
}
