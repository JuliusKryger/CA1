package facades;

import dtos.HobbiesDTO;
import dtos.PersonDTO;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;

public class PersonFacade implements InterfacePersonFacade {
    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade(){

    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf){
        if (instance == null){
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    //METODER
    //måske skal der laves test til
    public PersonDTO createPerson(String firstName, String lastName, String phoneNumber, String email, int age, String zipcode, ArrayList<HobbiesDTO> hobbies){
        EntityManager em = emf.createEntityManager();
        PersonDTO personDTO;
        try{
            personDTO = new PersonDTO(new Person(firstName, lastName, phoneNumber, email, age, zipcode, hobbies));
            em.getTransaction().begin();
            em.persist(personDTO);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return personDTO;
    }

    //test er lavet
    public PersonDTO getPersonByID(Integer id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person = em.find(Person.class, id);
        em.getTransaction().commit();
        em.close();

        if (person != null){
            return new PersonDTO(person);
        }else{
            return null;
        }
    }
}
