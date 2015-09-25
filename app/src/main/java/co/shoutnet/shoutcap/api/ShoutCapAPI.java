package co.shoutnet.shoutcap.api;

import co.shoutnet.shoutcap.model.ModelAdapterFont;
import retrofit.Callback;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Codelabs on 8/20/2015.
 */
public interface ShoutCapAPI {

    @Headers("Content-type: application/json")
    @POST("/api/api_getlistfont.php")
    void getDataFont(Callback<ModelAdapterFont> response);
}
