package entities;

import dtos.AddressDTO;
import dtos.CityInfoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "addresses")
@NamedQueries({
        @NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address"),
        @NamedQuery(name = "Address.getAllRows", query = "SELECT a from Address a"),
        @NamedQuery(name = "Address.getAddress", query = "SELECT a from Address a WHERE a.street = :street")
})
public class Address implements Serializable {

    //variables
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String street;
    private String additionalInfo;

    @OneToMany (mappedBy = "address")
    private List<Person> persons;
    @ManyToOne
    private CityInfo cityInfo;

    //Constructors
    public Address() {
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }

    public Address(String street, CityInfo cityInfo) {
        this.street = street;
        this.cityInfo = cityInfo;
    }

    public Address(AddressDTO dto) {
        this.street = dto.getStreet();
        this.additionalInfo = dto.getAdditionalInfo();
        this.cityInfo = new CityInfo(dto.getCityInfo());
    }

    //getter and setters
    public List<Person> getPersons() {
        return persons;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
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

    public void addPerson(Person person) {
        if (person != null) {
            person.setAddress(this);
            this.persons.add(person);
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", persons=" + persons +
                ", cityInfo=" + cityInfo +
                '}';
    }
}

