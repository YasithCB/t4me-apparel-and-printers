package model;

import java.time.LocalDate;
import java.util.Date;

public class Order {
    private String orderId;
    private int customerId;
    private int transactionId;
    private String trackingNo;
    private LocalDate date;
    private int qty;
    private double totalPrice;
    private String platform;
    private String paymentStatus;
    private String paymentMethod;
    private String deliveryStatus;

    public Order() {
    }

    public Order(String orderId, int customerId, int transactionId, String trackingNo, LocalDate date, int qty, double totalPrice, String platform, String paymentStatus, String paymentMethod, String deliveryStatus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.trackingNo = trackingNo;
        this.date = date;
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.platform = platform;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId=" + customerId +
                ", transactionId=" + transactionId +
                ", trackingNo='" + trackingNo + '\'' +
                ", date=" + date +
                ", qty=" + qty +
                ", totalPrice=" + totalPrice +
                ", platform='" + platform + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}
