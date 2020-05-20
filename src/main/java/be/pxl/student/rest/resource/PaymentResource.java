package be.pxl.student.rest.resource;

import be.pxl.student.entity.Label;

public class PaymentResource {
    private String counterAccount;
    private float amount;
    private String date;
    private String detail;
    private int id;
    private Label label;
    private String currency;

    public String getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(String counterAccount) {
        this.counterAccount = counterAccount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
