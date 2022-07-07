package model;

public class Design {
    private int designSku;
    private int sales;
    private String dateCreated;

    public Design() {
    }

    public Design(int designSku, int sales, String dateCreated) {
        this.designSku = designSku;
        this.sales = sales;
        this.dateCreated = dateCreated;
    }

    public int getDesignSku() {
        return designSku;
    }

    public void setDesignSku(int designSku) {
        this.designSku = designSku;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Design{" +
                "designSku=" + designSku +
                ", sales=" + sales +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
