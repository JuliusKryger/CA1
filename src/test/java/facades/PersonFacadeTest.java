package facades;
import dtos.*;
import entities.Address;
import entities.CityInfo;
import entities.Hobbies;
import entities.Person;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryDelegate;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

//import static junit.framework.Assert.assertEquals;

public class PersonFacadeTest {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1,p2,p3,p4;
    private PersonFacade personFacade;


    private static CityInfo cityInfo = new CityInfo("2650", "Hvidovre");
    private static CityInfoDTO cityInfoDTO = new CityInfoDTO(cityInfo);
    private static Address address = new Address ("valbyvej", 2);
    private static AddressDTO addressDTO = new AddressDTO(address);

    private PersonDTO personDTO1;

    @BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass()
    {
        //bruger en test database med drop-and-create,
        //derfor er det ikke nødvendigt at rydde op
    }

    @BeforeEach
    public void setUp() {
        //denne metode, gør alt klar inden man tester
        EntityManager em = emf.createEntityManager();

        Hobbies hobbies = new Hobbies("Handball", "wiki.dk", "General", "Indendørs");
        List <Hobbies> hobbiesList = new ArrayList<>();
        hobbiesList.add(hobbies);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows", Person.class);
           // p1 = new Person(1, "Kurt", "Verner");
           // p2 = new Person(2,"Anna", "Jørgensen");
            p3 = new Person(3,"Joe", "Johnson");
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
/*
    @Test
    //VIRKER, do not touch
    void getPersonByIDTest(){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            System.out.println("p1: " + p1.getId() + p1.getFirstName());
            PersonDTO result = facade.getPersonByID(p1.getId());
            assertEquals(p1.getFirstName(), result.getFirstName());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

 */

    @Test
    void deletePerson() {
        EntityManager em = emf.createEntityManager();
        try {
            int id = 1;
            System.out.println("this is the person we will delete" + p3.toString());
            facade.deletePerson(id);
            assertEquals(true, facade.deletePerson(id));
            System.out.println("This person should no longer exist" + p3.toString());
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetPersonById () throws Exception {
        System.out.println("getPerson");
        Integer id = p3.getId();
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        PersonDTO expResult = new PersonDTO(p3);
        PersonDTO result = instance.getPerson(id);
        assertEquals(expResult,result);



    }




/*
    @Test
    void editPersonBasisInformationTest(){
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            PersonDTO updated = facade.editPersonBasisInformation(new PersonDTO(p1));
            updated.setFirstName("Marie");
            updated.setLastName("Andersen Hansen");
            //updated.setPhoneNumber("034233434");
            //updated.setEmail("MAH@email.dk");
            //updated.setAge(22);
            System.out.println("p1" + p1.getFirstName());
            assertEquals("Marie", updated.getFirstName());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

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


}
