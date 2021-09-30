package dtos;

import entities.CityInfo;

import java.io.Serializable;

public class CityInfoDTO implements Serializable {
    private String zipCode;
    private String city;

    public CityInfoDTO(CityInfo cityInfo){
        if(cityInfo.getZipCode() != null)
            this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
