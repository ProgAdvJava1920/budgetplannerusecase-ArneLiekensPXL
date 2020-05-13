package be.pxl.student.dao;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;

import java.util.List;

public interface IPayment {
    List<Payment> getAll();
    Payment getById(int id);
    Payment addPayment(Payment payment);
    boolean updatePayment(Payment payment);
    boolean deletePayment(Payment payment);
}
