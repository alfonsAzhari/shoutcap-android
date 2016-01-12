package co.shoutnet.shoutcap;

import java.util.ArrayList;

import co.shoutnet.shoutcap.model.ModelAdapterVoucher;
import co.shoutnet.shoutcap.model.ModelNewsDetail;
import co.shoutnet.shoutcap.model.ModelOrderHistory;
import co.shoutnet.shoutcap.model.ModelVoucher;

/**
 * Created by Adam MB on 9/9/2015.
 */
public class Dummy {

    public ArrayList<ModelOrderHistory> getOrderHistory(){
        ArrayList<ModelOrderHistory>list = new ArrayList<>();
        ModelOrderHistory modelOrderHistory;

        modelOrderHistory = new ModelOrderHistory();
        modelOrderHistory.setOrderID("128371023");
        modelOrderHistory.setPrice(123456);
        modelOrderHistory.setPaymentStatus("Belum Dibayar");
        modelOrderHistory.setProductStatus("Belum");
        list.add(modelOrderHistory);

        modelOrderHistory = new ModelOrderHistory();
        modelOrderHistory.setOrderID("12837102123");
        modelOrderHistory.setPrice(123456);
        modelOrderHistory.setPaymentStatus("Belum Dibayar");
        modelOrderHistory.setProductStatus("Belum");
        list.add(modelOrderHistory);

        return list;
    }

//    public ArrayList<ModelAdapterVoucher> getVoucher(){
//        ArrayList<ModelAdapterVoucher>list = new ArrayList<>();
//        ModelAdapterVoucher modelVoucher;
//
//        modelVoucher = new ModelAdapterVoucher();
//        modelVoucher.setVoucherCode("lajshd0q837eu");
//        modelVoucher.setDiscount("10%");
//        modelVoucher.setDiscountTo("Baseball+Coor+Mixed+");
//        modelVoucher.setExpire("2014-07-13");
//        modelVoucher.setUseAtOrder("");
//        modelVoucher.setStatus("Belum digunakan");
//        list.add(modelVoucher);
//
//        modelVoucher = new ModelAdapterVoucher();
//        modelVoucher.setVoucherCode("lajshd0q837eu");
//        modelVoucher.setDiscount("10%");
//        modelVoucher.setDiscountTo("Baseball+Coor+Mixed+");
//        modelVoucher.setExpire("2014-07-13");
//        modelVoucher.setUseAtOrder("");
//        modelVoucher.setStatus("Belum digunakan");
//        list.add(modelVoucher);
//
//        return list;
//    }

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
