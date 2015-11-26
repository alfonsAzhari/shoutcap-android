package co.shoutnet.shoutcap.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.model.ModelColor;
import co.shoutnet.shoutcap.model.ModelSignIn;

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

    public static ModelSignIn getReturnSignIn(String json) throws IOException {
        gson = new Gson();

        return gson.fromJson(json, ModelSignIn.class);
    }
}
