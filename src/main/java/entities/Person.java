package entities;

import dtos.PersonDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.deleteAllRows", query = "DELETE FROM Person"),
        @NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p"),
        @NamedQuery(name = "Person.getByID", query = "SELECT p FROM Person p WHERE p.id = :id"),
        @NamedQuery(name = "Person.deletePersonById", query = "DELETE FROM Person p WHERE p.id = :id")
})
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String hobbies;
    private String phones;
    private String address;
    private String cityInfo;

    public Person(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person()
    {
    }

    public Person(Integer id, String firstName, String lastName, String email, String hobbies, String phones, String address, String cityInfo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = hobbies;
        this.phones = phones;
        this.address = address;
        this.cityInfo = cityInfo;
    }

    public Person(PersonDTO personDTO) {
        //TODO: NEEDS TO INITIALIZE SOME DATA.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //TODO: THIS NEEDS TO BE CHANGED TO WORK WITH CREATE PERSON getCityInfo()
    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }

    public void addHobby(Hobby ho) {
        //TODO: WE NEED ADD HOBBY METHOD IN PERSON ENTITY.
    }
}
