package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;
import java.util.List;

public class BudgetPlanner {
    public static void main(String[] args) {
        try {
            BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter(Path.of("src/main/resources/account_payments.csv"));
            budgetPlannerImporter.importData();
            List<Account> accounts = budgetPlannerImporter.getAccounts();
        } catch (Exception e) {
            LogManager.getLogger().error(e);
        }

    }

}
