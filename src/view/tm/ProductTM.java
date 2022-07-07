package view.tm;

import java.time.LocalDate;

public class ProductTM {
    private String productId;
    private String size;
    private String color;
    private int qtyOnHand;
    private LocalDate dateCreated;

    public ProductTM(String productId, String size, String color, int qtyOnHand, LocalDate dateCreated) {
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.qtyOnHand = qtyOnHand;
        this.dateCreated = dateCreated;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateAdded) {
        this.dateCreated = dateAdded;
    }

    @Override
    public String toString() {
        return "ProductTM{" +
                "productId='" + productId + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
