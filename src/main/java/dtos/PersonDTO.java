package dtos;
import entities.Address;
import entities.Person;

import java.util.List;

public class PersonDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List hobbies;
    private List phones;
    private Address address;
    private String cityInfo;


    public PersonDTO(Person person){
        if(person.getId() != null)
            this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.phones = person.getPhones();
        this.address = person.getAddress();
        this.cityInfo = person.getCityInfo();


    }

    //At the moment this has no purpose other than testing.
    public PersonDTO(String firstName, String lastName, String email, String hobbies, String phones, String address, String cityInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = hobbies;
        this.phones = phones;
        this.address = address;
        this.cityInfo = cityInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public List<HobbyDTO> getHobbies() {
        return hobbies;
    }*/

    public List getPhones() {
        return phones;
    }

    public void setPhones(List phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }

    public void setHobbies(List<HobbyDTO> h2) {
        //TODO: THIS METHOD NEEDS TO BE DONE.
    }
}
