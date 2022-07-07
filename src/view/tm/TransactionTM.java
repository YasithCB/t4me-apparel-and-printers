package view.tm;

import java.time.LocalDate;

public class TransactionTM {
    private int transactionId;
    private LocalDate Date;
    private double cost;
    private double income;
    private double balance;
    private String desc;

    public TransactionTM() {
    }

    public TransactionTM(int transactionId, LocalDate date, double cost, double income, double balance, String desc) {
        this.transactionId = transactionId;
        Date = date;
        this.cost = cost;
        this.income = income;
        this.balance = balance;
        this.desc = desc;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "TransactionTM{" +
                "transactionId=" + transactionId +
                ", Date='" + Date + '\'' +
                ", cost=" + cost +
                ", income=" + income +
                ", balance=" + balance +
                ", desc='" + desc + '\'' +
                '}';
    }
}
