package entities;

import dtos.CityInfoDTO;
import dtos.HobbiesDTO;
import dtos.PersonDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(name = "Person.deleteAllRows", query = "DELETE FROM Person"),
        @NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p"),
        @NamedQuery(name = "Person.getByID", query = "SELECT p FROM Person p WHERE p.id = :id"),
        @NamedQuery(name = "Person.deletePerson", query = "DELETE FROM Person p WHERE p.id = :id"),
        //ændre personens basis info, der skal laves en til hobbier og cityinformationer
        @NamedQuery(name = "Person.updatePerson", query = "UPDATE Person SET firstName = :firstName, lastName = :lastName, phoneNumber = :phoneNumber, email = :email, age = :age")
})
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int age;
    private CityInfoDTO cityInfoDTO;
    private ArrayList<HobbiesDTO> hobbies = new ArrayList<>();

    public Person(){

    }

    public Person(String firstName, String lastName, String phoneNumber, String email, int age, CityInfoDTO cityInfoDTO, ArrayList<HobbiesDTO> hobbies){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.cityInfoDTO = cityInfoDTO;
        this.hobbies = hobbies;
    }

    public ArrayList<HobbiesDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<HobbiesDTO> hobbies) {
        this.hobbies = hobbies;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
