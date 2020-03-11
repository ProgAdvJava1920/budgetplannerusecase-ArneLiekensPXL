package be.pxl.student.util;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Random fake data generator.
 * This class will generate some random data
 */
public class BudgetPlannerFeeder {

	Faker faker = new Faker();
	private static Logger logger;
	List<String> accounts;
	List<String> IBANNumbers;

	public static void main(String[] args) throws IOException {
		logger = LogManager.getLogger();
		BudgetPlannerFeeder feeder = new BudgetPlannerFeeder();
		feeder.addData();
		String[] dataLines = feeder.generateLines(100);
		feeder.printLines(dataLines);
		feeder.saveFile("src/main/resources/account_payments.csv", dataLines);
	}

	private void addData() {
		accounts = new ArrayList<>();
		IBANNumbers = new ArrayList<>();
		accounts.add("Jos");
		accounts.add("Jef");
		accounts.add("Jan");
		accounts.add("Jak");

		for (int i = 0; i < 4; i++) {
			IBANNumbers.add(faker.finance().iban("BE"));
		}
	}

	private void saveFile(String csvFile, String[] dataLines) throws IOException {
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFile), StandardOpenOption.WRITE)) {
			for (String dataLine : dataLines) {
				writer.write(dataLine);
				writer.newLine();
			}
		}
	}

	private void printLines(String[] dataLines) {
		for (String line: dataLines) {
			System.out.println(line);
		}
	}

	private String[] generateLines(int total) {
		List<String> data = new ArrayList<>();
		addHeaderLine(data);
		for (int i = 0; i < total; i++) {
			StringBuffer buffer = new StringBuffer();
			Random random = new Random();
			int index = random.nextInt(4);
			logger.debug(index);
			buffer.append(accounts.get(index)).append(","); // Account name
			buffer.append(IBANNumbers.get(index)).append(","); // Account IBAN
			int indexCounter = random.nextInt(4);
			while (index == indexCounter) {
				indexCounter = random.nextInt(4);
			}
			logger.debug(indexCounter);
			buffer.append(IBANNumbers.get(indexCounter)).append(","); // Account IBAN
			buffer.append(faker.date()
					.past(new Random().nextInt(30) + 1, TimeUnit.DAYS))
					.append(","); // Transaction date between now and 30 days ago
			buffer.append(faker.number().randomDouble(2, -5000, 5000)).append(","); // Amount
			buffer.append("EUR").append(","); // Currency
			buffer.append(faker.lorem().sentence()); // Detail
			data.add(buffer.toString());
		}
		return data.toArray(new String[0]);
	}

	private void addHeaderLine(List<String> data) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Account name").append(",");
		buffer.append("Account IBAN").append(",");
		buffer.append("Counteraccount IBAN").append(",");
		buffer.append("Transaction date").append(",");
		buffer.append("Amount").append(",");
		buffer.append("Currency").append(",");
		buffer.append("Detail");
		data.add(buffer.toString());
	}

}
