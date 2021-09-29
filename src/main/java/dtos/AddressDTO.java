package dtos;

import entities.Address;
import java.io.Serializable;

public class AddressDTO implements Serializable {
    private String streetName;
    private int number;
    private CityInfoDTO cityInfoDTO;

    public AddressDTO(Address address){
        if (address.getStreetName() !=null)
            this.streetName = address.getStreetName();
        this.number = address.getNumber();
        this.cityInfoDTO = address.getCityInfoDTO();
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

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }
}
