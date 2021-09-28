package facades;

import dtos.HobbiesDTO;
import dtos.PersonDTO;

import java.util.ArrayList;

public interface InterfacePersonFacade {
    public PersonDTO createPerson(String firstName, String lastName, String phoneNumber, String email, int age, String zipcode, ArrayList<HobbiesDTO> hobbies);
    public PersonDTO getPersonByID(Integer id);
    /*
    * PersonDTO DeletePerson
    * PersonsDTO seeAllPersons
    * PersonDTO editPerson
    * PersonDTO seeHobbiesForOnePerson
    *
    * */
}
