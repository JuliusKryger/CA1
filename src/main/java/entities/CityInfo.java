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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "zipcode", length = 4, unique = true)
    private String zipCode;
    @Column(name = "city", length=35, unique = false)
    private String city;

    @OneToMany(mappedBy = "cityInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Address> addresses;

    public CityInfo() {
    }

    public CityInfo(Integer id, String zipCode, String city) {
        this.id = id;
        this.zipCode = zipCode;
        this.city = city;
        this.addresses = new ArrayList<>();
    }

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfo(String zipCode) {
        this.zipCode = zipCode;
    }

    public CityInfo(CityInfoDTO dto) {
        this.zipCode = dto.getZipCode();
        this.city = dto.getCity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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
