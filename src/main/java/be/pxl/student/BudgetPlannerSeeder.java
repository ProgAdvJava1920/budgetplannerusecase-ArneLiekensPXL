package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class BudgetPlannerSeeder {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        List<Account> accounts = null;
        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

        accounts = budgetPlannerImporter.importData(Path.of("src/main/resources/account_payments.csv"));
        HashMap<Payment, String> counterPayments = budgetPlannerImporter.getCounterAccounts();

        for (Account account:
                accounts) {
            for (Payment payment:
                    account.getPayments()) {
                String counter = counterPayments.get(payment);
                if(accounts.stream().anyMatch(r -> r.getIBAN().equals(counter))) {
                    Account counterAccount = accounts.stream().filter(r -> r.getIBAN().equals(counter)).findFirst().get();
                    payment.setCounterAccount(counterAccount);
                    counterAccount.addCounterPayment(payment);
                    LogManager.getLogger().debug(counterAccount);
                }

            }

        }

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("course");
            entityManager = entityManagerFactory.createEntityManager();

            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();



            for (Account account:
                 accounts) {
                entityManager.persist(account);
            }

            for (Account account: accounts) {
                for (Payment payment:account.getPayments()) {
                    entityManager.persist(payment);
                }
            }

            entityTransaction.commit();
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
