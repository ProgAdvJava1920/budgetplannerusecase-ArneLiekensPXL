package be.pxl.student.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Account {

    private String IBAN;
    private String name;
    private List<Payment> payments;
    private List<Payment> counterPayments;
    private int id;

    public Account() {
        this.payments = new ArrayList<>();
        this.counterPayments = new ArrayList<>();
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void addCounterPayment(Payment payment) {
        float amount = payment.getAmount();
        if(amount < 0) {
            amount = Math.abs(amount);
        } else {
            amount -= amount;
            amount -= amount;
        }
        Payment counterPayment = new Payment(payment.getDate(), amount, payment.getCurrency(), payment.getDetail());
        counterPayment.setCounterAccount(payment.getCounterAccount());
        counterPayment.setCounterAccountId(payment.getCounterAccountId());
        payment.setAccountId(payment.getAccountId());
        payment.setId(payment.getId());
        this.counterPayments.add(counterPayment);
    }

    public List<Payment> getCounterPayments() {
        return counterPayments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                ", payments=[" + payments.stream().map(Payment::toString).collect(Collectors.joining(",")) + "]}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(IBAN, account.IBAN) &&
                Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, name);
    }
}
