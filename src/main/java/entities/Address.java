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
    private CityInfoDTO cityInfoDTO;

    public Address(){

    }

    public Address(String streetName, int number, CityInfoDTO cityInfoDTO){
        this.streetName = streetName;
        this.number = number;
        this.cityInfoDTO = cityInfoDTO;
    }

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
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
}
