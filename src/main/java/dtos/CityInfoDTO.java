package dtos;

import entities.CityInfo;

public class CityInfoDTO {
    private String zipCode;
    private String city;

    public CityInfoDTO() {
    }

    public CityInfoDTO(CityInfo entity){
        if(entity.getZipCode() != null)
            this.zipCode = entity.getZipCode();
        this.city = entity.getCity();
    }

    public CityInfoDTO(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
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

    @Override
    public String toString() {
        return "CityInfoDTO{" +
                "zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
