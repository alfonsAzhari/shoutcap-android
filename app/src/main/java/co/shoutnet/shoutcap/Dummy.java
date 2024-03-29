package co.shoutnet.shoutcap;

import java.util.ArrayList;

import co.shoutnet.shoutcap.model.ModelAdapterInbox;
import co.shoutnet.shoutcap.model.ModelAdapterNews;
import co.shoutnet.shoutcap.model.ModelAdapterRack;
import co.shoutnet.shoutcap.model.ModelAdapterReward;
import co.shoutnet.shoutcap.model.ModelAdapterCart;

/**
 * Created by Codelabs on 8/25/2015.
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

    public static ArrayList<ModelAdapterInbox> getDataInbox() {
        ArrayList<ModelAdapterInbox> list = new ArrayList<>();
        ModelAdapterInbox modelAdapterInbox;

        modelAdapterInbox = new ModelAdapterInbox("Pesan Satu", "12/12/12", "Isi Pesan Satu");
        list.add(modelAdapterInbox);

        modelAdapterInbox = new ModelAdapterInbox("Pesan Dua", "12/12/12", "Isi Pesan Dua");
        list.add(modelAdapterInbox);

        modelAdapterInbox = new ModelAdapterInbox("Pesan Tiga", "12/12/12", "Isi Pesan Tiga");
        list.add(modelAdapterInbox);

        return list;
    }

    public static ArrayList<ModelAdapterReward> getDataReward() {
        ArrayList<ModelAdapterReward> list = new ArrayList<>();
        ModelAdapterReward modelAdapterReward;

        modelAdapterReward = new ModelAdapterReward("Reward", "12/12/12", "1000", "1000");
        list.add(modelAdapterReward);

        modelAdapterReward = new ModelAdapterReward("Reward", "12/12/12", "1000", "1000");
        list.add(modelAdapterReward);

        modelAdapterReward = new ModelAdapterReward("Reward", "12/12/12", "1000", "1000");
        list.add(modelAdapterReward);

        modelAdapterReward = new ModelAdapterReward("Reward", "12/12/12", "1000", "1000");
        list.add(modelAdapterReward);

        return list;
    }
    public static ArrayList<ModelAdapterRack> getDataRack() {
        ArrayList<ModelAdapterRack> list = new ArrayList<>();
        ModelAdapterRack modelAdapterRack;

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg1);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg2);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg3);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg4);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg3);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg1);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg3);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg2);
        list.add(modelAdapterRack);

        modelAdapterRack=new ModelAdapterRack(R.drawable.bg4);
        list.add(modelAdapterRack);

        return list;
    }
    public static ArrayList<ModelAdapterCart> getDataCart() {
        ArrayList<ModelAdapterCart> list = new ArrayList<>();
        ModelAdapterCart modelCart;

        modelCart=new ModelAdapterCart(R.drawable.bg1,"ShoutCap","Rp. 300.300.00","Rp. 300.300.00");
        list.add(modelCart);

        modelCart=new ModelAdapterCart(R.drawable.bg2,"ShoutCap","Rp. 300.300.00","Rp. 300.300.00");
        list.add(modelCart);

        modelCart=new ModelAdapterCart(R.drawable.bg3,"ShoutCap","Rp. 300.300.00","Rp. 300.300.00");
        list.add(modelCart);

        modelCart=new ModelAdapterCart(R.drawable.bg4,"ShoutCap","Rp. 300.300.00","Rp. 300.300.00");
        list.add(modelCart);

        return list;
    }
}
