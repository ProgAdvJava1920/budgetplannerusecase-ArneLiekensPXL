package be.pxl.student.dao;

import be.pxl.student.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDao implements IAccount{
    private EntityManager entityManager;

    public AccountDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Account> getAll() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Account> query = entityManager.createNamedQuery("account.getAll", Account.class);
        List<Account> accounts = query.getResultList();
        transaction.commit();
        return accounts;
    }

    @Override
    public Account getById(int id) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Account account = entityManager.find(Account.class, id);
        transaction.commit();
        return account;
    }

    @Override
    public Account addAccount(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(account);

        transaction.commit();

        return account;
    }

    @Override
    public boolean updateAccount(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.merge(account);

        transaction.commit();

        return true;
    }

    @Override
    public boolean deleteAccount(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.remove(account);

        transaction.commit();

        return true;
    }
}
