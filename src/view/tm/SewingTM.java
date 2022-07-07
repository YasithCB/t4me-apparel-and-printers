package view.tm;

import java.time.LocalDate;

public class SewingTM {
    private String matId;
    private String productId;
    private int transactionId;
    private int matQty;
    private LocalDate date;
    private int noOfTshirts;
    private double costPerTshirt;
    private double sewCost;
    private String desc;

    public SewingTM() {
    }

    public SewingTM(String matId, String productId, int transactionId, int matQty, LocalDate date, int noOfTshirts, double costPerTshirt, double sewCost, String desc) {
        this.matId = matId;
        this.productId = productId;
        this.transactionId = transactionId;
        this.matQty = matQty;
        this.date = date;
        this.noOfTshirts = noOfTshirts;
        this.costPerTshirt = costPerTshirt;
        this.sewCost = sewCost;
        this.desc = desc;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getMatQty() {
        return matQty;
    }

    public void setMatQty(int matQty) {
        this.matQty = matQty;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNoOfTshirts() {
        return noOfTshirts;
    }

    public void setNoOfTshirts(int noOfTshirts) {
        this.noOfTshirts = noOfTshirts;
    }

    public double getCostPerTshirt() {
        return costPerTshirt;
    }

    public void setCostPerTshirt(double costPerTshirt) {
        this.costPerTshirt = costPerTshirt;
    }

    public double getSewCost() {
        return sewCost;
    }

    public void setSewCost(double sewCost) {
        this.sewCost = sewCost;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Sewing{" +
                "matId='" + matId + '\'' +
                ", productId='" + productId + '\'' +
                ", transactionId=" + transactionId +
                ", matQty=" + matQty +
                ", date=" + date +
                ", noOfTshirts=" + noOfTshirts +
                ", costPerTshirt=" + costPerTshirt +
                ", sewCost=" + sewCost +
                ", desc='" + desc + '\'' +
                '}';
    }
}
