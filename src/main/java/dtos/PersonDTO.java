package dtos;
import entities.Person;

import java.util.ArrayList;

public class PersonDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int age;
    private AddressDTO addressDTO;
    private HobbiesListDTO hobbies;


    public PersonDTO(Person person){
        if(person.getId() != null)
            this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phoneNumber = person.getPhoneNumber();
        this.email = person.getEmail();
        this.age = person.getAge();
        this.addressDTO = person.getAddressDTO();
        this.hobbies = person.getHobbiesListDTO();
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public HobbiesListDTO getHobbies() {
        return hobbies;
    }

    public void setHobbies(HobbiesListDTO hobbies) {
        this.hobbies = hobbies;
    }
}
