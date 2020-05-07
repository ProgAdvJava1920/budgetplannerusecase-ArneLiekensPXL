package be.pxl.student;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class BudgetPlanner {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try{
            entityManagerFactory = Persistence.createEntityManagerFactory("course");
            entityManager = entityManagerFactory.createEntityManager();

            AccountDao accountDao = new AccountDao(entityManager);
            PaymentDao paymentDao = new PaymentDao(entityManager);

            //implement code here

        } finally {
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
