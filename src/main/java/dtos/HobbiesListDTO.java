package dtos;

import entities.Hobbies;
import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class HobbiesListDTO {
    List<HobbiesDTO> all = new ArrayList();

    public HobbiesListDTO(List<Hobbies> hobbiesEntities) {
        hobbiesEntities.forEach((h) -> {
            all.add(new HobbiesDTO(h));
        });
    }
}
