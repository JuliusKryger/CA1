package dtos;

import entities.Address;

import java.util.Objects;

public class AddressDTO {
    private String street;
    private String additionalInfo;
    private CityInfoDTO cityInfo;
    String zip;
    String city;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressDTO() {
    }

    public AddressDTO(String street, String additionalInfo, CityInfoDTO cityInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.cityInfo = cityInfo;
    }

    public AddressDTO(String street, String addInfo, String zip, String cityInfo){
        this.street = street;
        this.additionalInfo = addInfo;
        this.zip = zip;
        this.city = city;
    }

    public AddressDTO(Address entity) {
        this.street = entity.getStreet() == null ? null : entity.getStreet();
        this.additionalInfo = entity.getAdditionalInfo();
        this.cityInfo = entity.getCityInfo() == null ? null : new CityInfoDTO(entity.getCityInfo());
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    public CityInfoDTO getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoDTO cityInfo) {
        this.cityInfo = cityInfo;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "street='" + street + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", cityInfo=" + cityInfo +
                '}';
    }
}
