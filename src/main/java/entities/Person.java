package entities;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
@NamedQueries({
        @NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person"),
        @NamedQuery(name = "Person.getAllRows", query = "SELECT p from Person p"),
        @NamedQuery(name = "Person.getPerson", query = "SELECT p from Person p WHERE p.id = :id"),
        @NamedQuery(name = "Person.deletePersonById", query = "DELETE FROM Person p WHERE p.id = :id")
})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "firstname", length = 175, nullable = false, unique = false)
    private String firstName;
    @Column(name = "lastname", length = 175, nullable = false, unique = false)
    private String lastName;
    @Column(name = "email", length = 175, nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Phone> phones;

    @ManyToOne
    private Address address;

    @ManyToMany(mappedBy = "persons", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Hobby> hobbies;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phones = new ArrayList<>();
        this.hobbies = new ArrayList<>();
        this.address = null;
    }

    public Person(PersonDTO dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phones = dto.getPhones() != null ? getNumberList(dto.getPhones()) : new ArrayList<>();
        this.hobbies = dto.getHobbies() != null ? getHobbyList(dto.getHobbies()) : new ArrayList<>();
        this.address = dto.getAddress() != null ? new Address(dto.getAddress()) : null;
    }

    public Person(String firstName, String lastName, String email, List<Phone> phones, Address address, List<Hobby> hobbies) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = phones;
        this.address = address;
        this.hobbies = hobbies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }


    //Methods
    public void addHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.add(hobby);
            hobby.getPersons().add(this);
        }
    }

    public void addPhone(Phone phone) {
        if (phone != null) {
            phone.setPerson(this);
            this.phones.add(phone);
        }
    }

    public void removeHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.remove(hobby);
            hobby.getPersons().remove(this);
        }
    }

    public List<Phone> getNumberList(List<PhoneDTO> phoneDTOS) {
        ArrayList<Phone> list = new ArrayList<>();
        for (PhoneDTO p : phoneDTOS) {
            list.add(new Phone(p.getNumber()));
        }
        return list;
    }

    public List<PhoneDTO> getPhonesDTOList(List<Phone> phones) {
        ArrayList<PhoneDTO> list = new ArrayList<>();
        if (phones != null) {
            for (Phone p : phones) {
                list.add(new PhoneDTO(p.getNumber()));
            }
        }
        return list;
    }

    public List<Hobby> getHobbyList(List<HobbyDTO> hobbyDTOS) {
        ArrayList<Hobby> list = new ArrayList<>();
        for (HobbyDTO h : hobbyDTOS) {
            list.add(new Hobby(h.getName(), h.getWikiLink(), h.getCategory(), h.getType()));
        }
        return list;
    }

    public List<HobbyDTO> getHobbyDTOList(List<Hobby> hobby) {
        ArrayList<HobbyDTO> list = new ArrayList<>();
        for (Hobby h : hobby) {
            list.add(new HobbyDTO(h));
        }
        return list;
    }

    public void replaceHobbies(ArrayList<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                ", address=" + address +
                ", hobbies=" + hobbies +
                '}';
    }
}