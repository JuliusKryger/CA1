package facades;

import dtos.*;

import java.util.ArrayList;

public interface InterfacePersonFacade {
    PersonDTO createPerson(int id, String firstName, String lastName);
    PersonDTO getPerson (Integer id);
    PersonDTO getAllPersons();

    PersonDTO editPersonBasisInformation(PersonDTO personDTO);
    PersonsDTO seeAllPersons();
    //public PersonDTO seeHobbiesForOnePerson(PersonDTO personDTO);
    /*
    * PersonsDTO seeAllPersons
    * PersonDTO editCityInformation
    * PersonDTO editHobbiesForPerson
    *
    * */
}
