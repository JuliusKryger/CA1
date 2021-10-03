package facades;

import dtos.*;
import entities.*;
import utils.Utility;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

public class PersonFacade {
    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade() {

    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Checks if a given phone number already exists.
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
            throw new WebApplicationException("ERROR: 500", 500);
        } finally {
            em.close();
        }
    }

    //checks if the email is already taken.
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
            throw new WebApplicationException("ERROR: 500", 500);
        } finally {
            em.close();
        }
    }

    //This method gets a single person based on ID.
    /* //TODO: THIS IS ACTUALLY JUST DUPLICATE CODE SEE BELOW FOR ACTUAL METHOD.
    public PersonDTO getPerson (int id){
        EntityManager em = emf.createEntityManager();
        try{
            Person person1 = em.find(Person.class, id);
            PersonDTO pdto1 = new PersonDTO(person1);
            return pdto1;
        }finally {
            em.close();
        }
    }

    //This method get all persons.
    public List<Person> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("Select person from Person person", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
*/

    //This finds a person from an given ID.
    public PersonDTO getPersonByID(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person = em.find(Person.class, id);
        em.getTransaction().commit();
        em.close();
        if (person != null) {
            person.setId(id);
            return new PersonDTO(person);
        } else {
            return null;
        }
    }

    /* //TODO: WELL THIS METHOD IS SMARTER, BUT IT NEED SOME PREREQUISITES.
    public PersonDTO getPersonById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);

        if (person != null) {
            List<Phone> phones = getPhoneByPersonId(id);
            List<Hobby> hobbies = getHobbyByPersonId(id);
            Address address = getAddressByPersonId(id);

            person.setPhones(phones);
            person.setHobbies(hobbies);
            person.setAddress(address);
            return new PersonDTO(person);
        } else {
            throw new WebApplicationException(String.format("No person with provided id: (%d) found", id), 400);
        }
    }
     */

    //This method get all persons.
    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phones pp JOIN p.hobbies ph JOIN p.address pa", Person.class);
        return Utility.convertList(PersonDTO.class, query.getResultList());
    }

    //This is the method we use to create a person.
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
                if (person.getAddress() != null && person.getAddress().getCityInfo() != null) {
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
                if (person.getHobbies() != null) {
                    for (HobbyDTO h : hobbies) {
                        Hobby ho = createHobby(h);
                        em.find(Hobby.class, ho.getName());
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
            throw new WebApplicationException("Invalid data ... ", 400);
        }
    }

    private Hobby createHobby(HobbyDTO hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name", Hobby.class);
            query.setParameter("name", hobby.getName());
            //query.setParameter("wikiLink", hobby.getWikiLink());
            //query.setParameter("category", hobby.getCategory());
            //query.setParameter("type", hobby.getType());
            return (Hobby) query.getSingleResult();
        } catch (NoResultException ex) {
            Hobby h = new Hobby(hobby);
            em.getTransaction().begin();
            em.persist(h);
            em.getTransaction().commit();
            return h;
        } finally {
            em.close();
        }
    }

    //TODO: ONLY BASIS INFOMATION? CAN WE PLEASE EXPAND THIS METHOD TO EDIT ALL PERSON INFO.
    public synchronized PersonDTO updatePerson(Integer id, PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        Person updated = em.find(Person.class, personDTO.getId());

        try {
            em.getTransaction().begin();
            updated.setFirstName(personDTO.getFirstName());
            updated.setLastName(personDTO.getLastName());
            em.merge(updated);
            em.getTransaction().commit();
            return new PersonDTO(updated);
        } finally {
            em.close();
        }

    }

    //TODO: THIS ONE NEEDS SOME WORK DONE.
    public synchronized PersonDTO editAddressForPerson(PersonDTO personToEdit) {
        EntityManager em = emf.createEntityManager();
        PersonDTO personDTO = getPersonByID(personToEdit.getId());
        Person updated = em.find(Person.class, personDTO.getId());

        try {
            em.getTransaction().begin();
            updated.setAddress(personToEdit.getAddress());
            updated.setCityInfo(personToEdit.getCityInfo());
            em.merge(updated);
            em.getTransaction().commit();
            return new PersonDTO(updated);
        } finally {
            em.close();
        }
    }

    //TODO: THIS ONE ALSO NEEDS SOME FIXIN' (BUT DO WE EVEN NEED IT?)
    public synchronized PersonDTO editPersonPhone(PersonDTO personToEdit) {
        EntityManager em = emf.createEntityManager();
        PersonDTO personDTO = getPersonByID(personToEdit.getId());
        Person updated = em.find(Person.class, personDTO.getId());
        try {
            em.getTransaction().begin();
            updated.setPhones(personToEdit.getPhones());
            em.merge(updated);
            em.getTransaction().commit();

            return new PersonDTO(updated);
        } finally {
            em.close();
        }
    }

    //TODO: QUITE CERTAIN WE CAN JUST REMOVE THIS ONE.
    public synchronized PersonDTO addHobbiesToPerson(Integer id) {
        EntityManager em = emf.createEntityManager();
        PersonDTO personDTO = getPersonByID(id);
        try {
            em.getTransaction().begin();

            em.getTransaction().commit();
            return personDTO;
        } finally {
            em.close();
        }
    }

    // deleteHobbiesFromPerson
    public synchronized PersonDTO deleteHobbiesFromPerson(Integer id) {
        EntityManager em = emf.createEntityManager();
        PersonDTO personDTO = getPersonByID(id);
        try {
            em.getTransaction().begin();

            em.getTransaction().commit();
            return personDTO;
        } finally {
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


    //TODO: THIS IS VERY IMPORTANT - WE STILL NEED SOME GETTER'S. EX.
    // public List<Phone> getPhoneByPersonId(Integer id)
    // public List<Hobby> getHobbyByPersonId(Integer id)
    // public Address getAddressByPersonId(Integer id)
    // public List<PersonDTO> getPersonListByZip(CityInfo cityInfo)
    // public List<PersonDTO> getPersonListByHobby(String hobbyname)
}
