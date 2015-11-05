package co.shoutnet.shoutcap.model;

import android.net.Uri;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class ModelAdapterRack {
    private Uri imgRack;

    public ModelAdapterRack() {
    }

    public ModelAdapterRack(Uri imgRack) {
        this.imgRack = imgRack;
    }

    public Uri getImgRack() {
        return imgRack;
    }

    public void setImgRack(Uri imgRack) {
        this.imgRack = imgRack;
    }
}
