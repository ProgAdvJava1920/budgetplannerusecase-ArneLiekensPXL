package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetPlannerImporterTest {
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
        List<Account> expected = new ArrayList<>();
        Account account = new Account();
        account.setName("Jos");
        account.setIBAN("BE69771770897312");
        Account account2 = new Account();
        account2.setName("Joske");
        account2.setIBAN("BE29805643594076");
        expected.add(account);
        expected.add(account2);
        assertEquals(planner.importData(Path.of("src/main/resources/two_accounts.csv")), expected);
    }
}
