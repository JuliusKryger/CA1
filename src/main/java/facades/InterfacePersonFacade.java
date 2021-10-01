package facades;

import dtos.*;

import java.util.ArrayList;

public interface InterfacePersonFacade {
    //PersonDTO createPerson(int id, String firstName, String lastName);
    PersonDTO getPersonByID(Integer id);
    PersonDTO editPersonBasisInformation(PersonDTO personDTO);
    PersonDTO editAddressForPerson(Integer id);
    PersonsDTO seeAllPersons();
    //public PersonDTO seeHobbiesForOnePerson(PersonDTO personDTO);
    /*
    * PersonsDTO seeAllPersons
    * PersonDTO editCityInformation
    * PersonDTO editHobbiesForPerson
    *
    * */
}
