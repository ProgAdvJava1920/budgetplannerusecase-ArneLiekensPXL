package be.pxl.student.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String IBAN;
    private String name;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "account")
    private List<Payment> payments;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "account")
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
        this.counterPayments.add(payment);
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
