package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.sql.*;
import java.time.LocalDateTime;

public class PaymentDao {
    private static final String SELECT_BY_ID = "SELECT * FROM payment WHERE id = ?";
    private static final String SELECT_BY_ACCOUNT_ID = "SELECT * FROM payment WHERE accountId = ?";
    private static final String UPDATE = "UPDATE payment SET date=?, amount=?, currency=?, detail=?, accountId=? WHERE id = ?";
    private static final String INSERT = "INSERT INTO payment (date, amount, currency, detail, accountId, counterAccountId) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM payment WHERE id = ?";
    private String url;
    private String user;
    private String password;

    public PaymentDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Payment readByAccountId(int accountId) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ACCOUNT_ID)) {
            stmt.setLong(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return mapPayment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Payment createPayment(Payment payment, Account account, Account counterAccount) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, new Date(payment.getDate().getYear(), payment.getDate().getMonthValue(), payment.getDate().getDayOfMonth()));
            stmt.setFloat(2, payment.getAmount());
            stmt.setString(3, payment.getCurrency());
            stmt.setString(4, payment.getDetail());
            stmt.setInt(5, account.getId());
            stmt.setInt(6, counterAccount.getId());
            if (stmt.executeUpdate() == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if(rs.next()) {
                        payment.setId(rs.getInt("id"));
                        return payment;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePayment(Payment payment, Account account) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setDate(1, new Date(payment.getDate().getYear(), payment.getDate().getMonthValue(), payment.getDate().getDayOfMonth()));
            stmt.setFloat(2, payment.getAmount());
            stmt.setString(3, payment.getCurrency());
            stmt.setString(4, payment.getDetail());
            stmt.setInt(5, account.getId());
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePayment(int id) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setLong(1, id);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Payment readPayment(int id)  {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return mapPayment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private Payment mapPayment(ResultSet rs) throws SQLException {

        Payment payment = new Payment(rs.getTimestamp("date").toLocalDateTime(), rs.getFloat("amount"), rs.getString("currency"), rs.getString("detail"));
        payment.setId(rs.getInt("id"));


        return payment;
    }
}
