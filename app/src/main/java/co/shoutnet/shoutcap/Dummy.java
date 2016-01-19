package co.shoutnet.shoutcap;

import java.util.ArrayList;

import co.shoutnet.shoutcap.model.ModelAdapterInbox;
import co.shoutnet.shoutcap.model.ModelAdapterNews;
import co.shoutnet.shoutcap.model.ModelAdapterReward;
import co.shoutnet.shoutcap.model.ModelNewsDetail;

/**
 * Created by Adam MB on 9/9/2015.
 */
public class Dummy {

    public static ArrayList<ModelAdapterNews> getDataNews() {
        ArrayList<ModelAdapterNews> list = new ArrayList<>();
        ModelAdapterNews modelAdapterNews;

        modelAdapterNews = new ModelAdapterNews("Kriminalitas Dikalangan Anak TK", "12/12/12", R.drawable.download);
        list.add(modelAdapterNews);

        modelAdapterNews = new ModelAdapterNews("Kenakalan Anak SD Menyebabkan Presiden MATI", "13/12/12", R.drawable.download);
        list.add(modelAdapterNews);

        return list;
    }
}
