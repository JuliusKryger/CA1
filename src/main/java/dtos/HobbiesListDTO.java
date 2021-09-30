package dtos;

import entities.Hobbies;
import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class HobbiesListDTO {

    List<HobbiesDTO> all = new ArrayList();

    public HobbiesDTO(List<Hobbies> hobbiesEntities){
        hobbiesEntities.forEach((p) ->{
            all.add(new HobbiesDTO(p));
        });
    }
}
