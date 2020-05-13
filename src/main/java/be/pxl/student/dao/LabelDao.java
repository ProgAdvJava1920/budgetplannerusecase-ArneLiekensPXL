package be.pxl.student.dao;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class LabelDao implements ILabel {
    private EntityManager entityManager;

    public LabelDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Label> getAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Label> query = entityManager.createNamedQuery("label.getAll", Label.class);
        List<Label> labels = query.getResultList();

        transaction.commit();

        return labels;
    }

    @Override
    public Label getById(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Label label = entityManager.find(Label.class, id);

        transaction.commit();

        return label;
    }

    @Override
    public Label addLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(label);

        transaction.commit();

        return label;
    }

    @Override
    public boolean updateLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(label);

        transaction.commit();

        return true;
    }

    @Override
    public boolean deleteLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(label);

        transaction.commit();

        return true;
    }
}
