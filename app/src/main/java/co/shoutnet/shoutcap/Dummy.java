package co.shoutnet.shoutcap;

import java.util.ArrayList;

/**
 * Created by Adam MB on 9/9/2015.
 */
public class Dummy {
    public ArrayList<ModelVoucher> getVoucher(){
        ArrayList<ModelVoucher>list = new ArrayList<>();
        ModelVoucher modelVoucher;

        modelVoucher = new ModelVoucher();
        modelVoucher.setVoucherCode("lajshd0q837eu");
        modelVoucher.setDiscount("10% to Baseball+Coor+Mixed+");
        modelVoucher.setExpire("2014-07-13");
        modelVoucher.setUseAtOrder("Belum Digunakan");
        modelVoucher.setStatus(true);
        list.add(modelVoucher);

        modelVoucher = new ModelVoucher();
        modelVoucher.setVoucherCode("lajshd0q837eu");
        modelVoucher.setDiscount("10% to Baseball+Coor+Mixed+");
        modelVoucher.setExpire("2014-07-13");
        modelVoucher.setUseAtOrder("Belum Digunakan");
        modelVoucher.setStatus(false);
        list.add(modelVoucher);

        return list;
    }

    public ArrayList<ModelNewsDetail> getNewsDetail() {
        ArrayList<ModelNewsDetail> list = new ArrayList<>();
        ModelNewsDetail modelNewsDetail;

        modelNewsDetail = new ModelNewsDetail();
        modelNewsDetail.setImage(R.drawable.bg1);
        modelNewsDetail.setTitle("Title Title Title");
        modelNewsDetail.setDate("23-9-2016");
        modelNewsDetail.setInfo("ajkshefaohdfoaisdhfajsdhfkahsdfalsdjhfoaisufowiehflajhdfoqueh");
        list.add(modelNewsDetail);

        return list;
    }

    public ArrayList<ModelInboxDetail> getInboxDetail() {
        ArrayList<ModelInboxDetail> list = new ArrayList<>();
        ModelInboxDetail modelInboxDetail;

        modelInboxDetail = new ModelInboxDetail();
        modelInboxDetail.setTitle("Title Title Title");
        modelInboxDetail.setDate("23-9-2016");
        modelInboxDetail.setContent("ajkshefaohdfoaisdhfajsdhfkahsdfalsdjhfoaisufowiehflajhdfoqueh");
        list.add(modelInboxDetail);

        return list;
    }
}