package dtos;

import entities.Address;

public class AddressDTO {
    private String street;
    private String additionalInfo;
    private CityInfoDTO cityInfo;

    public AddressDTO() {
    }

    public AddressDTO(String street, String additionalInfo, CityInfoDTO cityInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.cityInfo = cityInfo;
    }

    public AddressDTO(Address entity) {
        this.street = entity.getStreet();
        this.additionalInfo = entity.getAdditionalInfo();
        if (entity.getCityInfo() == null) {
            new CityInfoDTO(entity.getCityInfo());
        }
        this.cityInfo = entity.getCityInfo();
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
