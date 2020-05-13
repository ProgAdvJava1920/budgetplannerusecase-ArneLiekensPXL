package be.pxl.student.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name="label.getAll", query = "SELECT l FROM Label as l")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "label")
    private List<Payment> payments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
