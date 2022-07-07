package view.tm;

public class MaterialTM {
    private String materialId;
    private String materialName;
    private String color;
    private int gsm;
    private int qty;
    private String dateCreated;

    public MaterialTM() {
    }

    public MaterialTM(String materialId, String materialName, String color, int gsm, int qty, String dateCreated) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.color = color;
        this.gsm = gsm;
        this.qty = qty;
        this.dateCreated = dateCreated;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "MaterialTM{" +
                "materialId='" + materialId + '\'' +
                ", materialName='" + materialName + '\'' +
                ", color='" + color + '\'' +
                ", gsm=" + gsm +
                ", qty=" + qty +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
