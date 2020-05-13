package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.nio.file.Path;
import java.util.*;

public class BudgetPlannerSeeder {
    public static void main(String[] args) {
      /*  EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        List<Account> accounts = null;
        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

        accounts = budgetPlannerImporter.importData(Path.of("src/main/resources/account_payments.csv"));
        HashMap<Payment, String> counterPayments = budgetPlannerImporter.getCounterAccounts();
        List<Label> labels = getLabels();
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
                    Random random = new Random();
                    int labelIndex = random.nextInt(4);
                    LogManager.getLogger().debug(labelIndex);
                    payment.setLabel(labels.get(labelIndex));
                    labels.get(labelIndex).getPayments().add(payment);
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

            for(Label label :
                labels) {
                entityManager.persist(label);
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
*/

    }

    private static List<Label> getLabels() {
        Label label = new Label();
        label.setName("Kleding");
        Label label2 = new Label();
        label2.setName("Voeding");
        Label label3 = new Label();
        label3.setName("Vervoer");
        Label label4 = new Label();
        label4.setName("Drank");
        List<Label> labels = new ArrayList<>();

        labels.add(label);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);

        return labels;
    }
}
