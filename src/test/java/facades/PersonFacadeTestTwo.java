package facades;

import dtos.PersonDTO;
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
        //Setting up the Phone Array and populating it for our Person const.
        ArrayList phonesList = new ArrayList<Phone>();
        Phone po = new Phone(25758290, "privat");
        phonesList.add(po);

        //Creating an CityInfo Object for our Address Object.
        CityInfo ci = new CityInfo("3400", "Hillerød");

        //Creating an Address Object for our Person Const.
        Address address = new Address("Milnersvang 42", ci);

        //Lastly we're setting up an array of hobbies for the person const.
        ArrayList hobbyList = new ArrayList<Hobby>();
        Hobby h = new Hobby("Fodbold", "www.fodbold.dk", "boldsport", "undendørs");
        hobbyList.add(h);

        //PERSON CONSTRUCTOR: - String email, String firstName, String lastName, List<Phone> phones, Address address, List<Hobby> hobbies
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            p1 = new Person("Henrik", "Hansen", "23@dk.dk", phonesList, address, hobbyList);
            p2 = new Person("Micheal", "Laudrup", "Juleglad@dk.dk", phonesList, address, hobbyList);
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
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
        List<Person> arrayContent = null;
        arrayContent.add(p1);
        arrayContent.add(p2);
        try {
            em.getTransaction().begin();
            List<PersonDTO> result = facade.getAllPersons();
            assertEquals(false, result.isEmpty());
            assertEquals(true, result.containsAll(arrayContent));
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
        Address address = new Address("Søvænget 58", ci);

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
        } finally {
            em.close();
        }
    }

    @Test
    void updatePerson() {
        EntityManager em = emf.createEntityManager();
        try {
            //Henrik Hansen besluttede sig for et køns skifte.
            p1.setFirstName("Marie");
            p1.setLastName("Andersen");

            em.getTransaction().begin();
            PersonDTO updated = new PersonDTO(p1);
            updated = facade.updatePerson(1, updated);

            assertEquals("Marie", updated.getFirstName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void deletePerson() {
        EntityManager em = emf.createEntityManager();
        try {
            int id = p2.getId();
            System.out.println("this is the person we will delete" + p2.getFirstName());
            facade.deletePerson(id);
            assertEquals(true, facade.deletePerson(id));
            System.out.println("This person should no longer exist" + p2.getFirstName());
        } finally {
            em.close();
        }
    }
}