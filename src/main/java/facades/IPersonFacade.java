package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import dtos.PhoneDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public interface IPersonFacade {
    //This is implemented as a kind of security, as this will hide the actual logic of our methods.
    ////TODO: When doing (Rest-Endpoints) This is the methods we wanna call.

    //Enter Comment Here!
    PersonDTO getPersonById(Integer id);

    //Enter Comment Here!
    PersonsDTO getAllPersons();

    //Enter Comment Here!
    PersonDTO createPerson(PersonDTO personDTO);

    //Enter Comment Here!
    PersonDTO updatePerson(PersonDTO personDTO);

    PersonDTO editPersonPhone(int id, PhoneDTO phoneDTO);

    PersonDTO editAddressForPerson (int id, AddressDTO addressDTO);

    //Enter Comment Here!
    boolean deletePersonById(int id);

    //static PersonFacade getPersonFacade(EntityManagerFactory _emf);

}
