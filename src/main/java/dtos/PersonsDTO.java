package dtos;


import entities.Person;

import java.util.ArrayList;
import java.util.List;

// this class collects all persons
public class PersonsDTO {

    //TODO: Would be nice if we could just remove this class.
    List<PersonDTO> all = new ArrayList();

    public PersonsDTO(List<Person> personEntities) {
        personEntities.forEach((p) -> {
            all.add(new PersonDTO(p));
        });
    }
}


