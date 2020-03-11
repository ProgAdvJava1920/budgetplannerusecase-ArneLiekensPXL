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

public class BudgetPlannerSeeder {
    public static void main(String[] args) {
        List<Account> accounts = null;
        try {
            BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

            accounts = budgetPlannerImporter.importData(Path.of("src/main/resources/account_payments.csv"));
            AccountDao accountDao = new AccountDao("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
            PaymentDao paymentDao = new PaymentDao("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
            LogManager.getLogger().debug(accounts.size());
            for (Account account : accounts) {
                account = accountDao.createAccount(account);
            }
            for (Account account: accounts) {
                for (Payment payment:account.getPayments()) {
                    try {
                        Account counterAccount = accounts.stream().filter(r -> r.getIBAN().equals(payment.getCounterAccount())).findFirst().get();
                        LogManager.getLogger().debug(counterAccount);
                        paymentDao.createPayment(payment, account, counterAccount);
                    } catch (NoSuchElementException e) {
                        System.out.println("CounterAccount does not exist");
                    }
                }
            }
        } catch (Exception e) {
            LogManager.getLogger().error(e);
        }


    }
}
