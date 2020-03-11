package be.pxl.student;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;
import java.util.List;

public class BudgetPlanner {
    public static void main(String[] args) {
        List<Account> accounts = null;
        try {
            BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

            accounts = budgetPlannerImporter.importData(Path.of("src/main/resources/account_payments.csv"));
        } catch (Exception e) {
            LogManager.getLogger().error(e);
        }

        AccountDao accountDao = new AccountDao("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
        PaymentDao paymentDao = new PaymentDao("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
        
        for (Account account: accounts) {
            account = accountDao.createAccount(account);
            for (Payment payment:account.getPayments()) {
                paymentDao.createPayment(payment, account);
            }
        }
        System.out.println(paymentDao.readPayment(1));

    }

}
