package co.shoutnet.shoutcap.model;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class ModelAdapterCart {
    private int imgCart;
    private String txtProduct;
    private int txtPrice;
    private long txtSubTotal;

    public ModelAdapterCart(int imgCart, String txtProduct, int txtPrice, long txtSubTotal){
        this.imgCart=imgCart;
        this.txtProduct=txtProduct;
        this.txtPrice=txtPrice;
        this.txtSubTotal=txtSubTotal;
    }

    public int getImgCart() {
        return imgCart;
    }

    public void setImgCart(int imgCart) {
        this.imgCart = imgCart;
    }

    public int getTxtPrice() {
        return txtPrice;
    }

    public void setTxtPrice(int txtPrice) {
        this.txtPrice = txtPrice;
    }

    public String getTxtProduct() {
        return txtProduct;
    }

    public void setTxtProduct(String txtProduct) {
        this.txtProduct = txtProduct;
    }

    public long getTxtSubTotal() {
        return txtSubTotal;
    }

    public void setTxtSubTotal(long txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
    }
}
