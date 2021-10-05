package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public interface IPersonFacade {
    //This is implemented as a kind of security, as this will hide the actual logic of our methods.
    ////TODO: When doing (Rest-Endpoints) This is the methods we wanna call.

    //Enter Comment Here!
    PersonDTO getPersonByID(Integer id);

    //Enter Comment Here!
    PersonsDTO getAllPersons();

    //Enter Comment Here!
    PersonDTO createPerson(PersonDTO personDTO);

    //Enter Comment Here!
    PersonDTO updatePerson(PersonDTO personDTO);

    PersonDTO editPersonPhone(int phone, String description, PersonDTO personDTO);

    PersonDTO editAddressForPerson (String street, String addInfo, String zipCode, String city, PersonDTO personDTO);

    //Enter Comment Here!
    boolean deletePerson(int id);

    //static PersonFacade getPersonFacade(EntityManagerFactory _emf);

}
