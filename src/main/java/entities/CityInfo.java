package entities;

import dtos.CityInfoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cityinfo")
@NamedQueries({
        @NamedQuery(name = "CityInfo.deleteAllRows", query = "DELETE from CityInfo"),
        @NamedQuery(name = "CityInfo.getAllRows", query = "SELECT ci from CityInfo ci"),
        @NamedQuery(name = "CityInfo.getCityInfo", query = "SELECT ci from CityInfo ci WHERE ci.zipCode = :zipCode")
})
public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "zipCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String zipCode;
    private String city;
    @OneToMany
    private List<Address> addresses;

    public CityInfo() {
    }

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
        this.addresses = new ArrayList<>();
    }

    public CityInfo(String zipCode) {
        this.zipCode = zipCode;
    }

    public CityInfo(CityInfoDTO dto) {
        this.zipCode = dto.getZipCode();
        this.city = dto.getCity();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public void addAddress(Address address) {
        if (address != null) {
            address.setCityInfo(this);
            this.addresses.add(address);
        }
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
