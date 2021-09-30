package facades;

import dtos.*;

import java.util.ArrayList;

public interface InterfacePersonFacade {
    PersonDTO createPerson(int id, String firstName, String lastName);
    public PersonDTO getPersonByID(Integer id);
    public PersonDTO editPersonBasisInformation(PersonDTO personDTO);
    public PersonsDTO seeAllPersons();
    //public PersonDTO seeHobbiesForOnePerson(PersonDTO personDTO);
    /*
    * PersonsDTO seeAllPersons
    * PersonDTO editCityInformation
    * PersonDTO editHobbiesForPerson
    *
    * */
}
