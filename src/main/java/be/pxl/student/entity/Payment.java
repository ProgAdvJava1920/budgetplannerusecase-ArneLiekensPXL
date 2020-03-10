package be.pxl.student.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Payment {

    private LocalDateTime date;
    private float amount;
    private String currency;
    private String detail;
    private int id;

    public Payment(LocalDateTime date, float amount, String currency, String detail, int id) {
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    @Override
    public String toString() {
        return "{" +
                "date=" + date +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
