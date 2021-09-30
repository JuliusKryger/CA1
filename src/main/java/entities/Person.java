package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.deleteAllRows", query = "DELETE FROM Person"),
        @NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p"),
        @NamedQuery(name = "Person.getByID", query = "SELECT p FROM Person p WHERE p.id = :id"),
        @NamedQuery(name = "Person.deletePerson", query = "DELETE FROM Person p WHERE p.id = :id")
})
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;



}
