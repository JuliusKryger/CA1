package facades;

import dtos.*;
import entities.*;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class PersonFacadeTest {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3, p4;
    private PersonFacade personFacade;


    private static CityInfo cityInfo = new CityInfo("2650", "Hvidovre");
    private static CityInfoDTO cityInfoDTO = new CityInfoDTO(cityInfo);
    private static Address address = new Address("valbyvej", 2);
    private static AddressDTO addressDTO = new AddressDTO(address);

    private PersonDTO personDTO1;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
        EntityManager em = emf.createEntityManager();
        Phone phone = new Phone(874123, "number");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows", Person.class);
            p1 = new Person(1, "Kurt", "Verner", "hej@hej.dk", "Håndbold", phones, new Address("valbyvej", 2), "Kolding");
            p2 = new Person(2, "Anna", "Jørgensen", "hej@hej.dk", "Håndbold", phones, new Address("valbyvej", 2), "Kolding");
            p3 = new Person(3, "Joe", "Johnson", "hej@hej.dk", "Håndbold", phones, new Address("valbyvej", 2), "Kolding");
            p4 = new Person(4, "Suzuki", "Torben", "hej@hej.dk", "Håndbold", phones, new Address("valbyvej", 2), "Kolding");
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
        //bruger en test database med drop-and-create,
        //derfor er det ikke nødvendigt at rydde op
    }

    @BeforeEach
    public void setUp() {
        //denne metode, gør alt klar inden man tester
        Hobbies hobbies = new Hobbies("Handball", "wiki.dk", "General", "Indendørs");
        List<Hobbies> hobbiesList = new ArrayList<>();
        hobbiesList.add(hobbies);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows", Person.class);
            // p1 = new Person(1, "Kurt", "Verner");
            // p2 = new Person(2,"Anna", "Jørgensen");
            p3 = new Person(1, "Joe", "Johnson");
            // p4 = new Person(4,"Suzuki", "Torben");
            // em.persist(p1);
            // em.persist(p2);
            em.persist(p3);
            // em.persist(p4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
        //VIRKER, do not touch
    void getPersonByIDTest() {
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
    void deletePerson() {
        EntityManager em = emf.createEntityManager();
        try {
            int id = p3.getId();
            System.out.println("this is the person we will delete" + p3.getFirstName());
            facade.deletePerson(id);
            assertEquals(true, facade.deletePerson(id));
            System.out.println("This person should no longer exist" + p3.getFirstName());
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetPersonById() throws Exception {
        System.out.println("getPerson");
        int id = p3.getId();
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        PersonDTO expResult = new PersonDTO(p3);
        System.out.println("This is the test person we have created in our database, with an ID of: " + p3.getId().toString());
        PersonDTO result = instance.getPerson(id);
        System.out.println("This is the person we receive using our facade method, with an ID of: " + instance.getPerson(id).getId().toString());
        assertEquals(expResult.getId(), result.getId());


    }

/*
    @Test
    void editPersonBasisInformationTest(){
        EntityManager em = emf.createEntityManager();

        try{
            p1.setFirstName("Marie");
            p1.setLastName("Andersen Hansen");
            em.getTransaction().begin();

            PersonDTO updated = new PersonDTO(p1);
            updated = facade.editPersonBasisInformation(updated);
            //System.out.println("p1" + p1.getFirstName());
            assertEquals("Marie", updated.getFirstName());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    @Test
    void editPersonAddressTest(){
        EntityManager em = emf.createEntityManager();
        PersonDTO updated;
        PersonDTO personToUpdate = new PersonDTO(p2);
        try{
            personToUpdate.setAddress(new Address("malervej", 2));
            personToUpdate.setCityInfo("Randers");
            em.getTransaction().begin();
                updated = facade.editAddressForPerson(personToUpdate);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        assertEquals("Randers", updated.getCityInfo());
    }

    @Test
    void editPersonPhone(){
        EntityManager em  = emf.createEntityManager();
        PersonDTO updated;
        PersonDTO personToUpdate = new PersonDTO(p3);
        Phone phoneNumber = new Phone(9834334, "number");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(phoneNumber);
        try{
            personToUpdate.setPhones(phoneList);
            em.getTransaction().begin();
                updated = facade.editPersonPhone(personToUpdate);
            System.out.println("Person der ændres: " + updated.getFirstName());
            assertEquals(phoneList, updated.getPhones());

            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
/*
    @Test
    void seeAllPersonsTest(){
        EntityManager em = emf.createEntityManager();
        try{
            List<Person> persons = new ArrayList<>();
            persons.add(p1);
            persons.add(p2);
            persons.add(p3);
            persons.add(p4);
            PersonsDTO personsDTO = new PersonsDTO(persons);
            em.getTransaction().begin();
            PersonsDTO resultList = facade.seeAllPersons();
            assertEquals(resultList.toString(), personsDTO.toString());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

 */

    @Test //Not working seen as createPerson method is not done. (This is Jons test)
    public void testAddPerson() throws Exception {
        System.out.println("Testing ... add person method");
        String fName = "Jan";
        String lName = "Malkeko";
        String email = "MalkeJan@muh.dk";
        String hobby = "fluefisker";
        String phone = "30402053";
        String address = "malkekosvej 69";
        String cityInfo = "3200";
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        PersonDTO p1DTO = new PersonDTO(fName, lName, email, hobby, phone, address, cityInfo)
        PersonDTO result = instance.createPerson(p1DTO);
        PersonDTO expResult = new PersonDTO(fName, lName, email, hobby, phone, address, cityInfo);
        expResult.setId(result.getId());
        assertEquals(expResult, result);
    }


}
