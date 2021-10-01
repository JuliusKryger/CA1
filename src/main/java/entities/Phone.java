package entities;

import javax.persistence.*;

@Table(name = "phone")
@Entity
public class Phone {
    @Id
    @Column(name = "number", nullable = false)
    private Integer id;
    private int number;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Phone() {
    }
    public Phone(int number, String description){
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPerson(Person person) {
        //TODO: This method needs work.

    }
}