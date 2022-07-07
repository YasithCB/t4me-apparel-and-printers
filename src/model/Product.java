package model;

public class Product {
    private String productSku;
    private String color;
    private String size;
    private int qtyOnHand;

    public Product(String productSku, String color, String size, int qtyOnHand) {
        this.productSku = productSku;
        this.color = color;
        this.size = size;
        this.qtyOnHand = qtyOnHand;
    }

    public Product() {
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productSku='" + productSku + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
