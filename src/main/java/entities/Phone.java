package entities;

import dtos.PhoneDTO;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@NamedQueries({
        @NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone"),
        @NamedQuery(name = "Phone.getAllRows", query = "SELECT p from Phone p"),
        @NamedQuery(name = "Phone.getPhone", query = "SELECT p from Phone p WHERE p.id = :id")
})
public class Phone {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "number", length = 8, nullable = false, unique = true)
    private int number;
    @Column(name = "des", length = 175, nullable = true, unique = false)
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

    public Phone(PhoneDTO dto) {
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

                '}';
    }
}