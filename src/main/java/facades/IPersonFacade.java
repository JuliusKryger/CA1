package facades;

import dtos.PersonDTO;

import java.util.List;

public interface IPersonFacade {
    //This is implemented as a kind of security, as this will hide the actual logic of our methods.
    ////TODO: When doing (Rest-Endpoints) This is the methods we wanna call.

    //Enter Comment Here!
    PersonDTO getPersonByID(Integer id);

    //Enter Comment Here!
    List<PersonDTO> getAllPersons();

    //Enter Comment Here!
    PersonDTO createPerson(PersonDTO personDTO);

    //Enter Comment Here!
    PersonDTO updatePerson(Integer id, PersonDTO personDTO);

    //Enter Comment Here!
    boolean deletePerson(int id);

}
