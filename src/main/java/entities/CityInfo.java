package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 4)
    private String zipCode;
    @Column(length = 35)
    private String city;

    public CityInfo(){

    }

    public CityInfo(String zipCode, String city){
        this.zipCode = zipCode;
        this.city = city;

    }
}
