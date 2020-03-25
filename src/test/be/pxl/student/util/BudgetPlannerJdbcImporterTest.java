package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetPlannerJdbcImporterTest {
    BudgetPlannerImporter planner;

    @BeforeEach
    public void init() {
        planner = new BudgetPlannerImporter();
    }

    @Test
    public void importDataCreates2AccountsWhenNeeded() {
        assertEquals(planner.importData(Path.of("src/main/resources/two_accounts.csv")).size(), 2);
    }

    @Test
    public void importDataCreatesAccountListWithAccounts() {
   //     Jos,BE69771770897312,BE57930458353175,Wed Feb 19 22:11:33 CET 2020,3087.22,EUR,Sint deleniti consectetur impedit quidem.
    //    Joske,BE96771770897312,BE29805643594076,Sun Feb 09 23:49:54 CET 2020,-4492.37,EUR,Rerum tempore laboriosam pariatur possimus.
        List<Account> expected = new ArrayList<>();
        Account account = new Account();
        account.setName("Jos");
        account.setIBAN("BE69771770897312");
        account.addPayment(new Payment(LocalDateTime.of(2020, 2, 19, 22, 11, 33), (float) 3087.22, "EUR", "Sint deleniti consectetur impedit quidem."));
        Account account2 = new Account();
        account2.setName("Joske");
        account2.setIBAN("BE96771770897312");
        account2.addPayment(new Payment(LocalDateTime.of(2020, 2, 9, 23, 49, 54), (float) -4492.37, "EUR", "Rerum tempore laboriosam pariatur possimus."));
        expected.add(account);
        expected.add(account2);

        assertEquals(planner.importData(Path.of("src/main/resources/two_accounts.csv")), expected);
    }
}
