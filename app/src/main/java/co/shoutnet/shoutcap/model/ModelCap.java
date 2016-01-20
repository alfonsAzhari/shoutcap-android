package co.shoutnet.shoutcap.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CodeLabs on 26/11/2015.
 */
public class ModelCap {

    private String id;
    @SerializedName("img_path")
    private String imgPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
