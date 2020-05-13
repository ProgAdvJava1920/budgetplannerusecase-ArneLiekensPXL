package be.pxl.student.dao;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;

import java.util.List;

public interface ILabel {
    List<Label> getAll();
    Label getById(int id);
    Label getByName(String name);
    Label addLabel(Label label);
    boolean updateLabel(Label label);
    boolean deleteLabel(Label label);
}
