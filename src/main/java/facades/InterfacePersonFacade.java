package facades;

import dtos.*;

import java.util.ArrayList;

public interface InterfacePersonFacade {
    public PersonDTO createPerson(String firstName, String lastName, String phoneNumber, String email, int age, AddressDTO addressDTO, HobbiesListDTO hobbiesListDTO);
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
