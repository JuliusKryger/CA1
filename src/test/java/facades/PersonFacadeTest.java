package facades;
import dtos.CityInfoDTO;
import dtos.HobbiesDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import entities.Person;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

//import static junit.framework.Assert.assertEquals;

public class PersonFacadeTest {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1,p2,p3,p4;
    private PersonFacade personFacade;
    private static ArrayList<HobbiesDTO> hobbies = new ArrayList<>();
    private static CityInfo cityInfo = new CityInfo("2650", "Hvidovre");
    private static CityInfoDTO cityInfoDTO = new CityInfoDTO(cityInfo);

    @BeforeAll
    public static void setUpClass(){
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass(){
        //bruger en test database med drop-and-create,
        //derfor er det ikke nødvendigt at rydde op

    }
    @BeforeEach
    public void setUp() {
        //denne metode, gør alt klar inden man tester
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows", Person.class);
            p1 = new Person("Kurt", "Verner", "12345678", "hej@email.dk", 36, cityInfoDTO, hobbies);
            p2 = new Person("Anna", "Jørgensen", "87653421", "hej2@email.dk", 39, cityInfoDTO, hobbies);
            p3 = new Person("Joe", "Johnson", "65748234", "minEmail@email.dk", 28, cityInfoDTO, hobbies);
            p4 = new Person("Suzuki", "Torben", "95915284", "torben@email.dk", 54, cityInfoDTO, hobbies);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    //VIRKER, do not touch
    void getPersonByID(){
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


}

