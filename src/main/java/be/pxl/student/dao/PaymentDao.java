package be.pxl.student.dao;

import be.pxl.student.entity.Payment;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class PaymentDao implements IPayment {
    private EntityManager entityManager;

    public PaymentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Payment> getAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Payment> query = entityManager.createNamedQuery("payment.getAll", Payment.class);
        List<Payment> payments = query.getResultList();

        transaction.commit();

        return payments;
    }

    @Override
    public Payment getById(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Payment payment = entityManager.find(Payment.class, id);

        transaction.commit();

        return payment;
    }

    @Override
    public Payment addPayment(Payment payment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(payment);

        transaction.commit();

        return payment;
    }

    @Override
    public boolean updatePayment(Payment payment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(payment);

        transaction.commit();
        return true;
    }

    @Override
    public boolean deletePayment(Payment payment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(payment);

        transaction.commit();

        return true;
    }
}
