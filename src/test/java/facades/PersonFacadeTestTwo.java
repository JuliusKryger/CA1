package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import dtos.PhoneDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonFacadeTestTwo {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2;

    public PersonFacadeTestTwo() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//  Clean up database after test is done (Ehm Hopefully)
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.getTransaction().commit();


            Person p1 = new Person("Jack", "Nicklaus", "email@mail.dk");
            Phone phone = new Phone(35604570, "privat");
            Hobby hobby = new Hobby("Golf", "www.golf.dk", "club-and-ball", "undendørs");
            Address address = new Address();
            p1.addPhone(phone);
            p1.addHobby(hobby);
            p1.setAddress(address);
            CityInfo cityInfo = new CityInfo("3450", "Allerød");
            address.setCityInfo(cityInfo);

            PersonDTO pers = new PersonDTO(p1);
            em.persist(pers);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPersonByID() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            System.out.println("p1: " + p1.getId() + p1.getFirstName());
            PersonDTO result = facade.getPersonByID(p1.getId());
            assertEquals(p1.getFirstName(), result.getFirstName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getPersonByIdTwo() {
        System.out.println("getPerson");
        int id = p1.getId();
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        PersonDTO expResult = new PersonDTO(p1);
        System.out.println("This is the test person we have created in our database, with an ID of: " + p1.getId().toString());
        PersonDTO result = instance.getPersonByID(id);
        System.out.println("This is the person we receive using our facade method, with an ID of: " + instance.getPersonByID(id).getId().toString());
        assertEquals(expResult.getId(), result.getId());
    }

    @Test
    void getAllPersons() {
        EntityManager em = emf.createEntityManager();
        List<Person> arrayContent = new ArrayList<>();
        arrayContent.add(p1);
        arrayContent.add(p2);
        PersonsDTO listOfPeeps = new PersonsDTO(arrayContent);
        try {
            em.getTransaction().begin();
            PersonsDTO result = facade.getAllPersons();
            assertEquals(listOfPeeps, result);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @Test
    void createPerson() {
        EntityManager em = emf.createEntityManager();
        PersonDTO p3;
        Person entity;

        //Setting up the Phone Array and populating it for our Person const.
        ArrayList phonesList = new ArrayList<Phone>();
        Phone po = new Phone(69420420, "Arbejds");
        phonesList.add(po);

        //Creating an CityInfo Object for our Address Object.
        CityInfo ci = new CityInfo("3450", "Allerød");

        //Creating an Address Object for our Person Const.
        Address address = new Address("Søvænget 58", "5 th", ci);

        //Lastly we're setting up an array of hobbies for the person const.
        ArrayList hobbyList = new ArrayList<Hobby>();
        Hobby h = new Hobby("Golf", "www.golf.dk", "club-and-ball", "undendørs");
        hobbyList.add(h);

        //We have to do it this way as PersonDTO will only accept an entity.
        entity = new Person("Jack", "Nicklaus", "email@mail.dk", phonesList, address, hobbyList);
        p3 = new PersonDTO(entity);

        try {
            em.getTransaction().begin();
            PersonDTO result = facade.createPerson(p3);
            PersonDTO expResult = new PersonDTO(entity);
            assertEquals(expResult, result);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test
    void updatePerson() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Henrik Hansen besluttede sig for et køns skifte.
            p1.setFirstName("Marie");
            p1.setLastName("Andersen");
            em.getTransaction().begin();
            PersonDTO updated = new PersonDTO(p1);

            PersonDTO newPerson = facade.updatePerson(updated);
            assertEquals("Marie", newPerson.getFirstName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void deletePerson() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            int id = p2.getId();
            System.out.println("this is the person we will delete" + p2.getFirstName());
            facade.deletePerson(id);
            assertEquals(true, facade.deletePerson(id));
            System.out.println("This person should no longer exist" + p2.getFirstName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void editPersonPhone(){
        /* for at kunne teste denne metode, skal der bruges 2 personDTO
        * en liste derindeholder en phoneDTO, lavet udfra en int og en String
        * */
        EntityManager em = emf.createEntityManager();
        PersonDTO updated;
        PersonDTO personToUpdate =  new PersonDTO(p2);
        int phoneNumber = 723832;
        String description = "Arbejde";
        PhoneDTO phone = new PhoneDTO(phoneNumber, description);
        List<PhoneDTO> newPhoneNumber = new ArrayList<>();
        newPhoneNumber.add(phone);
        try{
            personToUpdate.setPhones(newPhoneNumber);
            em.getTransaction().begin();
            updated = facade.editPersonPhone(phoneNumber,description, personToUpdate);
            assertEquals(newPhoneNumber.get(0), updated.getPhones().get(0));
        }
        finally {
            em.close();
        }
    }

    @Test
    void updateAddress(){
        EntityManager em = emf.createEntityManager();
        PersonDTO updated;
        PersonDTO personToEdit = new PersonDTO(p2);
        String street = "villavej 3";
        String addInfo = "2th";
        String zipcode = "2650";
        String city = "Hvidovre";
        CityInfo cityInfo = new CityInfo(zipcode, city);
        Address address = new Address(street, addInfo, cityInfo);
        AddressDTO addressDTO = new AddressDTO(address);

        try{
            personToEdit.setAddress(addressDTO);
            em.getTransaction().begin();
            updated = facade.editAddressForPerson(street, addInfo, zipcode, city, personToEdit);
            assertEquals(personToEdit.getAddress().getStreet(),  updated.getAddress().getStreet());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }

    }

    @Test
    void getPersonListZip(){
        EntityManager em = emf.createEntityManager();
        List <Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);

        String zipcode = "3400";
        PersonsDTO personlistDTO = new PersonsDTO(personList);
        PersonsDTO personsDTO;
        try{
            em.getTransaction().begin();
            personsDTO = facade.getPersonListByZip(zipcode);
            assertEquals(personlistDTO.toString(), personsDTO.toString());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    @Test
    void addHobbies(){
        EntityManager em = emf.createEntityManager();
        String hobbyName = "ridning";
        Hobby hobby = new Hobby(hobbyName, "ridning.dk", "dyr", "udendørs");
        int id = p1.getId();
        try{
            em.getTransaction().begin();
            PersonDTO personDTO = facade.addHobbiesToPerson(id, hobbyName);
            assertEquals( "ridning", p1.getHobbies().get(1).toString());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }


}