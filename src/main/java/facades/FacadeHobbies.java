package facades;

import javax.persistence.EntityManagerFactory;

public class FacadeHobbies {

    private static FacadeHobbies instance;
    private static EntityManagerFactory emf;

    private FacadeHobbies() {

    }

    public static FacadeHobbies getFacadeHobbies(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeHobbies();
        }
        return instance;
    }


}
