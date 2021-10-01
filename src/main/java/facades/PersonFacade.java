package facades;

import dtos.*;
import entities.*;
import utils.Utility;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
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

    private synchronized boolean checkIfNumberExists(Phone phone) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Phone p WHERE p.number = :number", Phone.class);
            query.setParameter("number", phone.getNumber());
            phone = (Phone) query.getSingleResult();
            return phone != null;
        } catch (NoResultException ex) {
            return false;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    //The methods read the person
    public PersonDTO getPerson (int id){
        EntityManager em = emf.createEntityManager();
        try{
            PersonDTO person1 = em.find(PersonDTO.class, id);
            return person1;
        }finally {
            em.close();
        }
    }

    //
    public List <Person> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("Select person from Person person", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    private boolean isEmailTaken(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class);
            query.setParameter("email", personDTO.getEmail());
            Person person = (Person) query.getSingleResult();
            if (person != null) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException ex) {
            return false;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    //METODER
    //måske skal der laves test til
    public synchronized PersonDTO createPerson(PersonDTO personDTO) {
        if (Utility.ValidatePersonDto(personDTO) && !isEmailTaken(personDTO)) {
            Person person = null;
            List<HobbyDTO> hobbies = personDTO.getHobbies();
            List<HobbyDTO> h2 = new ArrayList<>();
            personDTO.setHobbies(h2);
            EntityManager em = emf.createEntityManager();
            try {
                person = new Person(personDTO);

                em.getTransaction().begin();
                //Why does this reference not work, person.getAddress().getCityInfo() //TODO: LOOK HERE.
                if(person.getAddress() != null && person.getAddress().getCityInfo() != null){
                    Address a = person.getAddress();
                    CityInfo ci = a.getCityInfo();
                    em.persist(ci);
                    a.setCityInfo(ci);
                    em.persist(a);
                }
                if (person.getPhones() != null) {
                    for (Phone p : person.getPhones()) {
                        if (!checkIfNumberExists(p)) {
                            em.persist(p);
                            p.setPerson(person);
                            em.merge(p);
                        }
                    }
                }

                em.persist(person);

                if(person.getHobbies() != null){
                    for(HobbyDTO h: hobbies){
                        Hobby ho = createHobby(h);
                        em.find(Hobby.class, ho.getId());
                        person.addHobby(ho);
                        em.merge(person);
                    }
                }

                em.merge(person);

                em.getTransaction().commit();

            } finally {
                em.close();
            }
            return new PersonDTO(person);
        } else {
            throw new WebApplicationException("Please check your data", 400);
        }
    }

    private Hobby createHobby(HobbyDTO h) {
        //TODO: HERE WE NEED TO CREATE A CREATE HOBBY METHOD.
        return null;
    }

    //test er lavet og virker
    public PersonDTO getPersonByID(Integer id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person = em.find(Person.class, id);
        em.getTransaction().commit();
        em.close();

        if (person != null){
            person.setId(id);
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
            em.createNamedQuery("Person.deletePersonById").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return true;
        } finally {
            em.close();
        }
    }
}
