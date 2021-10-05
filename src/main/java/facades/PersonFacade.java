package facades;

import dtos.*;
import entities.*;
import utils.Utility;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonFacade implements IPersonFacade {
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

    //TODO: CRUD Checklist.
    //Create - done ...
    //Read - done ...
    //Update - done ...
    //Delete - done ...
    // ... Which means we atleast have achived full crud.

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


    //TODO: This method should be removed
    public List<PersonDTO> getAllPersons2() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phones pp JOIN p.hobbies ph JOIN p.address pa", Person.class);
        return Utility.convertList(PersonDTO.class, query.getResultList());
    }

    //This method get all persons.
    public PersonsDTO getAllPersons(){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery <Person> typedQuery = em.createNamedQuery("Person.getAllRows", Person.class);
            List<Person> personList = typedQuery.getResultList();
            PersonsDTO personsDTO = new PersonsDTO(personList);
            em.getTransaction().commit();
            return personsDTO;
        }
        finally {
            em.close();
        }
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
                        HobbyDTO hobby = createHobby(h.getName(), h.getWikiLink(), h.getCategory(), h.getType());
                        em.find(Hobby.class, hobby.getName());
                        Hobby hentity = new Hobby(hobby);
                        person.addHobby(hentity);
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

    public HobbyDTO createHobby(String name, String link, String type, String category) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby();

        try {
            em.getTransaction().begin();
            hobby.setName(name);
            hobby.setCategory(category);
            hobby.setWikiLink(link);
            hobby.setType(type);
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

    public boolean deleteHobby (int id){
    EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Hobby h WHERE h.id = :id").setParameter("id", id).executeUpdate();
           // em.createNamedQuery("H").setParameter("id", hobby).executeUpdate();
            em.getTransaction().commit();
            return true;
        }finally {
            em.close();
        }

    }


    //til createhobby hvis det skal bruges
        /*try {
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
        }*/


    public synchronized PersonDTO updatePerson(PersonDTO personDTO) {
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
    //endpoint er lavet og tutor emil siger den virker
    public synchronized PersonDTO editAddressForPerson(int id, AddressDTO addressDTO) {
        EntityManager em = emf.createEntityManager();
        Person updated = em.find(Person.class, id);

        Address curAddress = updated.getAddress();
        CityInfo curCI = updated.getAddress().getCityInfo();

        curCI.setZipCode(addressDTO.getZip());
        curCI.setCity(addressDTO.getCity());
        curAddress.setStreet(addressDTO.getStreet());
        curAddress.setAdditionalInfo(addressDTO.getAdditionalInfo());

        try {
            em.getTransaction().begin();
            curAddress.setCityInfo(curCI);
            updated.setAddress(curAddress);
            em.merge(updated);
            em.getTransaction().commit();
            return new PersonDTO(updated);
        } finally {
            em.close();
        }
    }

    //endpoint er lavet
    public synchronized PersonDTO editPersonPhone(int id, PhoneDTO phoneDTO) {
        EntityManager em = emf.createEntityManager();
        Person updated = em.find(Person.class, id);

        List <Phone> phones= updated.getPhones();

        Phone newPhone = new Phone(phoneDTO);

        phones.set(phones.size()-1,newPhone);

        try {
            em.getTransaction().begin();
            updated.setPhones(phones);
            em.merge(updated);
            em.getTransaction().commit();

            return new PersonDTO(updated);
        } finally {
            em.close();
        }
    }


    //TODO: QUITE CERTAIN WE CAN JUST REMOVE THIS ONE or edit it, so i works
    public synchronized PersonDTO addHobbiesToPerson(Integer id, String hobbyName) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);

        try {
            em.getTransaction().begin();
            TypedQuery <Hobby> typedQuery = em.createQuery("SELECT h FROM Hobby h WHERE h.name =:name", Hobby.class);
            typedQuery.setParameter("name", hobbyName);
            Hobby hobby = typedQuery.getSingleResult();
            person.addHobby(hobby);
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
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

    //test er lavet, men ikke kørt
    public synchronized PersonsDTO getPersonListByZip(String zipCode){
        EntityManager em = emf.createEntityManager();
        List <Person> personList;
        List<Person> withGivenZip = new ArrayList<>();
        PersonsDTO personsDTO;
        try{
            em.getTransaction().begin();
            //TypedQuery <CityInfo> typedQuery = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode",CityInfo.class);
            //typedQuery.setParameter("zipCode", zipCode);
            TypedQuery <Person> typedQuery1 = em.createNamedQuery("Person.getAllRows",Person.class);
            personList = typedQuery1.getResultList();
            for (int i = 0; i < personList.size(); i++){
                Person person = personList.get(i);
                String foundZip = person.getAddress().getCityInfo().getZipCode();
                if(Objects.equals(zipCode, foundZip)){
                    withGivenZip.add(personList.get(i));
                    personsDTO = new PersonsDTO(withGivenZip);
                    return personsDTO;
                }
                else{
                    String text = "There is either not a zip with that value or persons with that zip";
                    System.out.println(text);
                }
            }
            em.getTransaction().commit();
            return null;

        }
        finally {
            em.close();
        }
    }


    //TODO: THIS IS IF WE HAVE EXTRA TIME (NICE TO HAVE BUT NOT NECESSARY) - WE STILL NEED SOME GETTER'S. EX.
    // public List<Phone> getPhoneByPersonId(Integer id)
    // public List<Hobby> getHobbyByPersonId(Integer id)
    // public Address getAddressByPersonId(Integer id)
    // public PersonsDTO getPersonListByHobbyName(String hn)
}
