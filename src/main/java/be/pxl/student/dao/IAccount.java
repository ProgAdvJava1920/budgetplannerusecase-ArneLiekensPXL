package be.pxl.student.dao;

import be.pxl.student.entity.Account;

import java.util.List;

public interface IAccount {
    List<Account> getAll();
    Account getById(int id);
    Account addAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(Account account);
}
