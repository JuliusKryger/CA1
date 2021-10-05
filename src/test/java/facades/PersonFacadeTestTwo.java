package facades;

import dtos.*;
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
    private ArrayList arrP = new ArrayList();
    private ArrayList arrH = new ArrayList();
    private Person p4;
    private Person p1 = new Person("email 1", "First 1", "Last 1");
    private Person p2 = new Person("email 2", "First 2", "Last 2");
    private Phone ph1 = new Phone(111, "Privat");
    private Phone ph2 = new Phone(222, "Arbejds");
    private Address a1 = new Address("street 1", "1 th.");
    private CityInfo c1 = new CityInfo("2000", "Frederiksberg");
    private Hobby h1 = new Hobby("name1", "wikiLink1", "category1", "type1");
    private Hobby h2 = new Hobby("name2", "wikiLink2", "category2", "type2");

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
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            arrP.add(ph1);
            arrH.add(h1);
            p4 = new Person("hej", "hej", "hej", arrP, a1, arrH);
            PersonDTO p1DTO = new PersonDTO(p4);
            //em.persist(p4);
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

    //virker
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

    //
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

    //virker
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

    //virker
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

    //virker
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

        String zipcode = "2650";
        PersonsDTO personlistDTO = new PersonsDTO(personList);
        PersonsDTO personsDTO;
        try{
            em.getTransaction().begin();
            personsDTO = facade.getPersonListByZip(zipcode);
            assertEquals(personlistDTO.equals(p2), personsDTO.equals(p2));
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
    
    @Test
    void createHobby(){
        EntityManager em = emf.createEntityManager();
        String name = "Tennis";
        String link = "tennis.dk";
        String type = "boldspil";
        String category = "udendørs";
        Hobby hobby;
        
        try {
            em.getTransaction().begin();
            hobby = new Hobby(name, link, category, type);
            HobbyDTO hobbyDTO = new HobbyDTO(hobby);
            assertEquals(hobbyDTO.getName(),"Tennis");
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }

    @Test
    void deleteHobby() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            String name = h1.getName();
            System.out.println("This hobby will be deleted" + h1.getName());
            facade.deleteHobby(h1.getId());
            assertEquals(true, facade.deleteHobby(h1.getId()));
            System.out.println("This hobby should not exist" + h1.getName());
            em.getTransaction().commit();
        }
    }
    
    
    
    
    

    @Test
    void addHobbies(){
        EntityManager em = emf.createEntityManager();
        String hobbyName = "ridning";
        //TODO: This needs to be an list not an object.
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