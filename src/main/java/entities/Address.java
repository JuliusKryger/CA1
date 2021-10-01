package entities;

import dtos.CityInfoDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Address implements Serializable {
    @Id
    @Column(name = "streetName", nullable = false)
    private String streetName;
    private int number;
    //TODO: HERE WE NEED CITYINFO.

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

    public void setCityInfo(CityInfo ci) {
        //TODO DELETE THIS AND REPLACE WITH REAL SET
    }

    public CityInfo getCityInfo() {
        //TODO DELETE THIS AND REPLACE WITH REAL GET
        return null;
    }
}

