package entities;

import dtos.CityInfoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Address implements Serializable {
    @Id
    @Column(name = "streetName", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String streetName;
    private int number;

    @ManyToOne
    private CityInfo cityInfo;

    @OneToMany
    private List<Person> persons;

    public Address(){

    }

    public Address(String streetName, int number) {
        this.streetName = streetName;
        this.number = number;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}

