package view.tm;

public class MaterialBuyingTM {
    private String materialId;
    private String materialName;
    private String supplierName;
    private String transactionId;
    private String date;
    private double transportCost;
    private double materialCost;
    private double totalCost;

    public MaterialBuyingTM() {
    }

    public MaterialBuyingTM(String materialId, String materialName, String supplierName, String transactionId, String date, double transportCost, double materialCost, double totalCost) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.supplierName = supplierName;
        this.transactionId = transactionId;
        this.date = date;
        this.transportCost = transportCost;
        this.materialCost = materialCost;
        this.totalCost = totalCost;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(double materialCost) {
        this.materialCost = materialCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "MaterialBuyingTM{" +
                "materialId='" + materialId + '\'' +
                ", materialName='" + materialName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", date='" + date + '\'' +
                ", transportCost=" + transportCost +
                ", materialCost=" + materialCost +
                ", totalCost=" + totalCost +
                '}';
    }
}
