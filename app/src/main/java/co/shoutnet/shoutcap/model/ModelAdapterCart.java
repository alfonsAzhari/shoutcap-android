package co.shoutnet.shoutcap.model;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class ModelAdapterCart {
    private int imgCart;
    private String txtProduct;
    private String txtPrice;
    private String txtSubTotal;

    public ModelAdapterCart(int imgCart, String txtProduct, String txtPrice, String txtSubTotal){
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

    public String getTxtProduct() {
        return txtProduct;
    }

    public void setTxtProduct(String txtProduct) {
        this.txtProduct = txtProduct;
    }

    public String getTxtPrice() {
        return txtPrice;
    }

    public void setTxtPrice(String txtPrice) {
        this.txtPrice = txtPrice;
    }

    public String getTxtSubTotal() {
        return txtSubTotal;
    }

    public void setTxtSubTotal(String txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
    }
}
