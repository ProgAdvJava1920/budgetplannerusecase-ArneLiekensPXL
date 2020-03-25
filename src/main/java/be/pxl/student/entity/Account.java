package be.pxl.student.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Entity
public class Account {

    @Id
    private int id;
    private String IBAN;
    private String name;
    @OneToMany
    private List<Payment> payments;
    @OneToMany
    private List<Payment> counterPayments;


    public Account() {
        this.payments = new ArrayList<>();
        this.counterPayments = new ArrayList<>();
    }

    public void setCounterPayments(List<Payment> counterPayments) {
        this.counterPayments = counterPayments;
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
        counterPayment.setCounterAccountString(payment.getCounterAccountString());
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
