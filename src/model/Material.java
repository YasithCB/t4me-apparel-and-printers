package model;

import java.time.LocalDate;

public class Material {
    private String matId;
    private String matName;
    private String color;
    private int gsm;
    private int qtyOnHand;
    private LocalDate dateCreated;

    public Material() {
    }

    public Material(String matId, String matName, String color, int gsm, int qtyOnHand, LocalDate dateCreated) {
        this.matId = matId;
        this.matName = matName;
        this.color = color;
        this.gsm = gsm;
        this.qtyOnHand = qtyOnHand;
        this.dateCreated = dateCreated;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getGsm() {
        return gsm;
    }

    public void setGsm(int gsm) {
        this.gsm = gsm;
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

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Material{" +
                "matId='" + matId + '\'' +
                ", matName='" + matName + '\'' +
                ", color='" + color + '\'' +
                ", gsm=" + gsm +
                ", qtyOnHand=" + qtyOnHand +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
