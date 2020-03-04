package be.pxl.student;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;
import java.util.List;

public class BudgetPlanner {
    public static void main(String[] args) {
        List<Account> accounts = null;
        try {
            BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

            accounts = budgetPlannerImporter.importData(Path.of("src/main/resources/two_accounts.csv"));
        } catch (Exception e) {
            LogManager.getLogger().error(e);
        }

        AccountDao accountDao = new AccountDao("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
        System.out.println(accountDao.readAccount(1));

    }

}
