package be.pxl.student.rest.resource;

import be.pxl.student.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public class GetPaymentsResource {
    private List<PaymentResource> payments;
    private float receivingAmount;
    private  float resultAmount;
    private float spendingAmount;

    public GetPaymentsResource() {
        this.payments = new ArrayList<>();
    }

    public List<PaymentResource> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResource> payments) {
        this.payments = payments;
    }

    public float getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(float receivingAmount) {
        this.receivingAmount = receivingAmount;
    }

    public float getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(float resultAmount) {
        this.resultAmount = resultAmount;
    }

    public float getSpendingAmount() {
        return spendingAmount;
    }

    public void setSpendingAmount(float spendingAmount) {
        this.spendingAmount = spendingAmount;
    }

    public void mapPayments(List<Payment> payments) {
        for (Payment payment:
             payments) {
            PaymentResource paymentResource = new PaymentResource();

            paymentResource.setAmount(payment.getAmount());
            paymentResource.setCounterAccount(payment.getCounterAccount().getIBAN());
            paymentResource.setDate(payment.getDate().toString());
            paymentResource.setDetail(payment.getDetail());
            paymentResource.setId(payment.getId());
            paymentResource.setLabel(payment.getLabel());

            this.payments.add(paymentResource);
        }
    }
}
