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
import java.util.Scanner;

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
            accounts.stream().filter(a -> a.getId() == counterAccountId).findFirst().get().addCounterPayment(payment);
            payment.setCounterAccount(accounts.stream().filter(a -> a.getId() == counterAccountId).findFirst().get().getIBAN());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name(x to stop): ");
        String name = scanner.nextLine();

        while (!name.toLowerCase().equals("x")) {
            try {
                String finalName = name;
                Account account = accounts.stream().filter(a -> a.getName().toLowerCase().equals(finalName.toLowerCase())).findFirst().get();
                System.out.println("Payments: ");
                for (Payment payment:account.getPayments()) {
                    System.out.println(payment);
                }

                System.out.println("Counter Payments: ");
                for (Payment payment : account.getCounterPayments()) {
                    System.out.println(payment);
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }

            System.out.println("Enter name(x to stop): ");
            name = scanner.nextLine();
        }
    }

}
