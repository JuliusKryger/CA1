package entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "hobby")
@Entity
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToMany
    private List<Person> persons;

    public List<Person> getPersons(){
        return persons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}