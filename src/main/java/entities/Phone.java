package entities;

import dtos.PhoneDTO;
import javax.persistence.*;

@Table(name = "phone")
@Entity
public class Phone {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "number", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;
    private String description;

    @ManyToOne
    private Person person;


    public Phone() {
    }

    public Phone(int number) {
        this.number = number;
    }

    public Phone(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(PhoneDTO dto){
        this.number = dto.getNumber();
        this.description = dto.getDescription();
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
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

    @Override
    public String toString() {
        return "Phone{" +
                "number=" + number +
                ", description='" + description + '\'' +
                ", person=" + person +
                '}';
    }
}