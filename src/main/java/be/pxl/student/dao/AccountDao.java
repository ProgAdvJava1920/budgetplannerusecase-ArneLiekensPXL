package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import jdk.jshell.spi.ExecutionControl;

import java.sql.*;

public class AccountDao {
    private static final String SELECT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String UPDATE = "UPDATE account SET name=?, IBAN=? WHERE id = ?";
    private static final String INSERT = "INSERT INTO account (name, IBAN) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM account WHERE id = ?";
    private String url;
    private String user;
    private String password;

    public AccountDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Account createAccount(Account account) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getIBAN());
            if (stmt.executeUpdate() == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if(rs.next()) {
                        account.setId(rs.getInt(1));
                        return account;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getIBAN());
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccount(int id) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setLong(1, id);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account readAccount(int id)  {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private Account mapAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setName(rs.getString("name"));
        account.setIBAN(rs.getString("IBAN"));
        account.setId(rs.getInt("id"));
        return account;
    }
}
