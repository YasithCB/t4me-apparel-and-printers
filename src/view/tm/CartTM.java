package view.tm;

public class CartTM {
    private String orderId;
    private String productId;
    private String designSku;
    private int qty;
    private double unitPrice;
    private double subTotal;

    public CartTM() {
    }

    public CartTM(String orderId, String productId, String designSku, int qty, double unitPrice, double subTotal) {
        this.orderId = orderId;
        this.productId = productId;
        this.designSku = designSku;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDesignSku() {
        return designSku;
    }

    public void setDesignSku(String designSku) {
        this.designSku = designSku;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
