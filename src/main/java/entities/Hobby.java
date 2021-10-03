package entities;

import dtos.HobbyDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "hobby")
@Entity
public class Hobby implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "name", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    @ManyToMany
    private List<Person> persons;

    public Hobby() {
    }

    public Hobby(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
        this.persons = new ArrayList<>();
    }

    public Hobby(HobbyDTO dto) {
        this.name = dto.getName();
        this.wikiLink = dto.getWikiLink();
        this.category = dto.getCategory();
        this.type = dto.getType();
        this.persons = new ArrayList<>();
    }

    public Hobby(String name) {
        this.name = name;
        this.persons = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Person> getPersons() {
        return persons;
    }

    /*
        public void addPerson(Person person){
            if(person != null){
                this.persons.add(person);
                person.getHobbies().add(this);
            }
        }

        public void removePerson(Person person){
            if(person != null){
                this.persons.add(person);
                person.getHobbies().remove(this);
            }
        }
     */
    @Override
    public String toString() {
        return "Hobby{" +
                "name='" + name + '\'' +
                ", wikiLink='" + wikiLink + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type;
    }
}