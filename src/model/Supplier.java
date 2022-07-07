package model;

import java.time.LocalDate;

public class Supplier {
    private String supName;
    private String address;
    private String contact;
    private String desc;
    private LocalDate date;

    public Supplier() {
    }

    public Supplier(String supName, String address, String contact, String desc, LocalDate date) {
        this.supName = supName;
        this.address = address;
        this.contact = contact;
        this.desc = desc;
        this.date = date;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supName='" + supName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", desc='" + desc + '\'' +
                ", date=" + date +
                '}';
    }
}
