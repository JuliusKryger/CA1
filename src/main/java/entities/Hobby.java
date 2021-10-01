package entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "hobby")
@Entity
public class Hobby {
    @Id
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    @ManyToMany
    private List<Person> persons;

    public List<Person> getPersons(){
        return persons;
    }

    public Integer getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }
}
