package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
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
    public Account getByName(String name) {
        Account account;
        EntityTransaction transaction = entityManager.getTransaction();
        Logger logger = LogManager.getLogger();
        transaction.begin();

        TypedQuery<Account> query = entityManager.createNamedQuery("account.getByName", Account.class);
        query.setParameter("name", name);
        try {
            account = query.getSingleResult();
        } catch (NoResultException e) {
            account = null;
        }
        transaction.commit();
        return account;
    }

    @Override
    public Account getByIban(String iban) {
        Account account;
        EntityTransaction transaction = entityManager.getTransaction();
        Logger logger = LogManager.getLogger();
        transaction.begin();

        TypedQuery<Account> query = entityManager.createNamedQuery("account.getByIban", Account.class);
        query.setParameter("iban", iban);
        try {
            account = query.getSingleResult();
        } catch (NoResultException e) {
            account = null;
        }
        transaction.commit();
        return account;
    }

    @Override
    public Account addAccount(Account account) {
        Logger logger = LogManager.getLogger();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        logger.debug(account);

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
