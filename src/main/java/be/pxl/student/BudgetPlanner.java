package be.pxl.student;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

public class BudgetPlanner {
    public static void main(String[] args) {
        AccountDao accountDao = new AccountDao("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
        PaymentDao paymentDao = new PaymentDao("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");

        List<Account> accounts = accountDao.read();
        List<Payment> payments = paymentDao.read();

        for (Payment payment:
             payments) {
            int accountId = payment.getAccountId();
            int counterAccountId = payment.getCounterAccountId();

            accounts.stream().filter(a -> a.getId() == accountId).findFirst().get().addPayment(payment);
            accounts.stream().filter(a -> a.getId() == counterAccountId).findFirst().get().addPayment(payment);
            payment.setCounterAccount(accounts.stream().filter(a -> a.getId() == counterAccountId).findFirst().get().getIBAN());
        }

        for (Account account:accounts) {
            System.out.println(account);
        }
    }

}
