package facades;

import dtos.*;
import entities.Hobbies;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

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
    public PersonDTO createPerson(int id, String firstName, String lastName){
        EntityManager em = emf.createEntityManager();
        PersonDTO personDTO;
        try{
            personDTO = new PersonDTO(new Person(id, firstName, lastName));
            em.getTransaction().begin();
            em.persist(personDTO);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return personDTO;
    }

    //test er lavet og virker
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

    @SuppressWarnings("unchecked")
    public PersonDTO editPersonBasisInformation(PersonDTO personDTO){
        EntityManager em = emf.createEntityManager();
        try{
            Person updated = em.find(Person.class, personDTO.getId());
            em.getTransaction().begin();
            updated.setFirstName(personDTO.getFirstName());
            updated.setLastName(personDTO.getLastName());
            //updated.setPhoneNumber(personDTO.getPhoneNumber());
            //updated.setEmail(personDTO.getEmail());
            //updated.setAge(personDTO.getAge());
            em.getTransaction().commit();
            return new PersonDTO(updated);
        }
        finally {
            em.close();
        }

    }

    @SuppressWarnings("unchecked")
    public PersonsDTO seeAllPersons() {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery <Person> typedQuery = em.createNamedQuery("Person.getAll", Person.class);
            List<Person> allPersons = typedQuery.getResultList();
            em.getTransaction().commit();
            return new PersonsDTO(allPersons);
        }
        finally {
            em.close();
        }
    }


    //return "{\"result\":\"" + FACADE.deletePersonById(id) + "\"}";
    //PersonResource.java
    public boolean deletePerson(int id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Person p WHERE p.id = :id").setParameter("id", id).executeUpdate();
            em.createNamedQuery("Person.deletePerson").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return true;
        } finally {
            em.close();
        }
    }
}
