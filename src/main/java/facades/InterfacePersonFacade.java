package facades;

import dtos.CityInfoDTO;
import dtos.HobbiesDTO;
import dtos.PersonDTO;

import java.util.ArrayList;

public interface InterfacePersonFacade {
    public PersonDTO createPerson(String firstName, String lastName, String phoneNumber, String email, int age, CityInfoDTO cityInfoDTO, ArrayList<HobbiesDTO> hobbies);
    public PersonDTO getPersonByID(Integer id);
    public PersonDTO editPersonBasisInformation(PersonDTO personDTO);
    /*
    * PersonDTO DeletePerson
    * PersonsDTO seeAllPersons
    * PersonDTO editCityInformation
    * PersonDTO editHobbiesForPerson
    * PersonDTO seeHobbiesForOnePerson
    *
    * */
}
