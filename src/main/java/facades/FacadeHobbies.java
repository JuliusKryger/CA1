package facades;

import entities.Hobbies;
import entities.Person;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class FacadeHobbies implements InterfaceHobbies {

    private static FacadeHobbies instance;
    private static EntityManagerFactory emf;

    private FacadeHobbies(){

    }

    public static FacadeHobbies getFacadeHobbies(EntityManagerFactory _emf){
        if (instance == null){
            emf = _emf;
            instance = new FacadeHobbies();
        }
        return instance;
    }



}
