package facades;

import dtos.*;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonFacadeTestTwo {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    Person p2, p4;
    /*
    private ArrayList phoneArray = new ArrayList();
    private ArrayList hobbyArray = new ArrayList();
    private Person p4;
    private Person p1 = new Person("email 1", "First 1", "Last 1");
    private Person p2 = new Person("email 2", "First 2", "Last 2");
    private Phone ph1 = new Phone(111, "Privat");
    private Phone ph2 = new Phone(222, "Arbejds");
    private CityInfo c1 = new CityInfo("2000", "Frederiksberg");
    private CityInfo c2 = new CityInfo("2000", "Frederiksberg");
    private Address a1 = new Address("street 1", "1 th.", c1);
    private Address a2 = new Address("street 1", "1 th.", c1);
    private Hobby h1 = new Hobby("name1", "wikiLink1", "category1", "type1");
    private Hobby h2 = new Hobby("name2", "wikiLink2", "category2", "type2");

     */
    private static Person p1 = new Person("Ole", "Sørensen", "oleelskerhunde@gmail.com");
    //private static Person p2 = new Person("Bente", "Rasmussen", "Bentehvem@hotmail.com");
    //private static Person p3 = new Person("Sofus", "Pedersen", "minlillemissekat@kat.dk");

    private static Phone ph1 = new Phone(12345678, "Private");
    //private static Phone ph2 = new Phone(87654321, "Private");
    //private static Phone ph3 = new Phone(88888888, "Private");

    private static CityInfo ci1 = new CityInfo("2850", "Nærum");
    //private static CityInfo ci2 = new CityInfo("2000", "Frederiksberg");
    //private static CityInfo ci3 = new CityInfo("3520", "Farum");

    private static Address a1 = new Address("Satsvej", "1 th.", ci1);
    //private static Address a2 = new Address("Blommevej", "2 tv.", ci2);
    //private static Address a3 = new Address("Solsikkehaven", "34", ci3);

    private static Hobby h1 = new Hobby("golf", "www.golf.dk", "ballplay", "ooutdoors");
    //private static Hobby h2 = new Hobby("softball", "www.softball.dk", "ballplay", "ooutdoors");
    //private static Hobby h3 = new Hobby("tennis", "www.tennis.dk", "ballplay", "ooutdoors");

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
            /*
            em.getTransaction().begin();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();

            //Creating our hobby Array.
            p1.addHobby(h1);
            p2.addHobby(h2);

            //Creating our phone Array.
            p1.addPhone(ph1);
            p2.addPhone(ph2);

            //Person 1
            em.persist(c1);
            em.persist(a1);
            a1.setCityInfo(c1);
            em.merge(a1);

            em.persist(p1);
            p1.setAddress(a1);
            em.merge(p1);

            //Person 2
            em.persist(c2);
            em.persist(a2);
            a2.setCityInfo(c2);
            em.merge(a2);

            em.persist(p2);
            p2.setAddress(a2);
            em.merge(p2);

            em.getTransaction().commit();

             */
            try {
                em.getTransaction().begin();
                //Creating our hobby Array.
                p1.addHobby(h1);
                //p1.addHobby(h2);

                //Creating our phone Array.
                p1.addPhone(ph1);
                //p2.addPhone(ph2);
                //p3.addPhone(ph3);

                //Person 1
                em.persist(ci1);
                em.persist(a1);
                a1.setCityInfo(ci1);
                em.merge(a1);

                em.persist(p1);
                p1.setAddress(a1);
                em.merge(p1);

//            //Person 2
//            em.persist(ci2);
//            em.persist(a2);
//            a2.setCityInfo(ci2);
//            em.merge(a2);
//
//            em.persist(p2);
//            p2.setAddress(a2);
//            em.merge(p2);
//
//            //Person 3
//            em.persist(ci3);
//            em.persist(a3);
//            a3.setCityInfo(ci3);
//            em.merge(a3);
//
//            em.persist(p3);
//            p3.setAddress(a3);
//            em.merge(p3);

                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }

    @AfterEach
    void tearDown() {
    }

    @Test //This test works do not touch the test or method anymore.
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

    @Test //This test works do not touch the test or method anymore.
    void getPersonByIdTwo() {
        System.out.println("getPerson");
        int id = 1;
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getPersonFacade(_emf);
        System.out.println("This is the test person we have created in our database, with an ID of: " + p1.getId().toString());
        PersonDTO result = instance.getPersonByID(id);
        System.out.println("This is the person we receive using our facade method, with an ID of: " + instance.getPersonByID(id).getId().toString());
        assertEquals(1, result.getId());
    }


    @Test //This dosen't currently work, we need to find a way to compare two array results preferbly object equals to method.
    void getAllPersons() {
        EntityManager em = emf.createEntityManager();
        List<Person> arrayContent = new ArrayList<>();
        arrayContent.add(p1);
        //arrayContent.add(p2);
        PersonsDTO listOfPeeps = new PersonsDTO(arrayContent);
        try {
            em.getTransaction().begin();
            PersonsDTO result = facade.getAllPersons();
            em.getTransaction().commit();
            System.out.println(result);
            assertEquals(listOfPeeps.getAll().size(), result.getAll().size());

        } finally {
            em.close();
        }
    }

    //
    @Test //Does not work need to be redone.
    void createPerson() {
        EntityManager em = emf.createEntityManager();
        PersonDTO p3;
        Person entity;

        //Setting up the Phone Array and populating it for our Person const.
        List<Phone> phonesList = new ArrayList<>();
        Phone po = new Phone(69420420, "Arbejds");
        phonesList.add(po);

        //Creating an CityInfo Object for our Address Object.
        CityInfo ci = new CityInfo("3450", "Allerød");

        //Creating an Address Object for our Person Const.
        Address address = new Address("Søvænget 58", "5 th", ci);

        //Lastly we're setting up an array of hobbies for the person const.
        List<Hobby> hobbyList = new ArrayList<>();
        Hobby h = new Hobby("Golf", "www.golf.dk", "club-and-ball", "undendørs");
        hobbyList.add(h);

        //We have to do it this way as PersonDTO will only accept an entity.
        entity = new Person("Jack", "Nicklaus", "email@mail.dk", phonesList, address, hobbyList);
        p3 = new PersonDTO(entity);

        try {
            em.getTransaction().begin();
            PersonDTO result = facade.createPerson(p3);
            PersonDTO expResult = new PersonDTO(entity);
            assertEquals(expResult.getId(), result.getId());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test //This test works do not touch the test or method anymore.
    void updatePerson() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Henrik Hansen besluttede sig for et køns skifte.
            p1.setFirstName("Marie");
            p1.setLastName("Andersen");
            PersonDTO updated = new PersonDTO(p1);

            PersonDTO newPerson = facade.updatePerson(updated);
            assertEquals("Marie", newPerson.getFirstName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test //Does not work, change back to boolean =)
    void deletePerson() {
        EntityManager em = emf.createEntityManager();
        PersonDTO personDTO = new PersonDTO(p1);
        try {
            em.getTransaction().begin();
            int id = p1.getId();
            System.out.println("this is the person we will delete" + p1.getFirstName());
            facade.deletePerson(id);
            assertEquals(personDTO, facade.deletePerson(id));
            System.out.println("This person should no longer exist" + p1.getFirstName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //virker
    /*@Test
    void editPersonPhone(){
        /* for at kunne teste denne metode, skal der bruges 2 personDTO
        * en liste der indeholder en phoneDTO, lavet udfra en int og en String
        * *//*
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
    }*/

    //virker
    /*@Test
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

    }*/

    /*
    @Test
    void getPersonListZip(){
        EntityManager em = emf.createEntityManager();
        List <Person> personList = new ArrayList<>();
        personList.add(p4);
        //personList.add(p2);

        String zipcode = "2000";
        PersonsDTO personlistDTO = new PersonsDTO(personList);
        PersonsDTO personsDTO;
        try{
            em.getTransaction().begin();
            personsDTO = facade.getPersonListByZip(zipcode);
            assertEquals(personlistDTO.equals(p4), personsDTO.equals(p4));
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
     */

    /*
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
            h1.setId(1);
            int id = h1.getId();
            System.out.println("This hobby will be deleted" + h1.getName());
            facade.deleteHobby(id);
            assertTrue(facade.deleteHobby(id));
            System.out.println("This hobby should not exist" + h1.getName());
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }


    @Test
    void addHobbies(){
        EntityManager em = emf.createEntityManager();
        String hobbyName = "ridning";
        Hobby hobby = new Hobby(hobbyName, "ridning.dk", "dyr", "udendørs");
        int id = p1.getId();
        List <Hobby> hobbies = new ArrayList<>();
        hobbies.add(hobby);
        p1.setHobbies(hobbies);

        try{
            em.getTransaction().begin();
            PersonDTO personDTO = facade.addHobbiesToPerson(id, hobbyName);
            assertEquals( "ridning", personDTO.getHobbies().get(1).toString());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

     */


}
