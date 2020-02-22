package be.pxl.student.util;

import be.pxl.student.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.*;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private Path filePath;
    private List<Account> accounts;
    private static Logger logger;

    public BudgetPlannerImporter(Path filePath) {
        this.filePath = filePath;
        accounts = new ArrayList<>();
        logger = LogManager.getLogger();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void importData() {
        logger.debug("Importing Data");
        try(BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line = null;
            int index = 0;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if(index != 0) {
                    createObjects(line);
                }
                index++;
            }
        } catch (IOException e) {
            logger.error(e);
        }
        logger.debug("Data imported");
    }

    private void createObjects(String line) {
        Account account;
        String[] seperatedLine = line.split(",");
        account = new Account();
        account.setIBAN(seperatedLine[1]);
        account.setName(seperatedLine[0]);

        if(accounts.contains(account)) {
            logger.debug("Account" + account.getName() + "already exists, adding new payment");
            int index = accounts.indexOf(account);
            accounts.get(index).getPayments().add(createPayment(seperatedLine));
            logger.debug("Payment added to " + account.getName());
        } else {
            logger.debug("Creating new Account");
            List<Payment> payments = new ArrayList<>();
            payments.add(createPayment(seperatedLine));
            account.setPayments(payments);
            accounts.add(account);
            logger.debug("New Account created: " + account.toString());
        }

    }

    private Payment createPayment(String[] seperatedLine) {
        logger.debug("Creating new Payment");
        String dateString = seperatedLine[3];
        float ammount = Float.parseFloat(seperatedLine[4]);
        String currency = seperatedLine[5];
        String details = seperatedLine[6];

        String[] dateArray = dateString.split(" ");
        String[] time = dateArray[3].split(":");

        LocalDateTime date = LocalDateTime.of(Integer.parseInt(dateArray[5]), MonthShort.valueOf(dateArray[1].toUpperCase()).getValue(), Integer.parseInt(dateArray[2]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));

        Payment payment = new Payment(date, ammount, currency, details);
        logger.debug("New Payment created: " + payment.toString());
        return payment;
    }
}
