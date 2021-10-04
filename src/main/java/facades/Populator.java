/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import utils.EMF_Creator;

import java.util.ArrayList;

/**
 * @author tha
 */
public class Populator {
    private static Person p1, p2;
    public static void populate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));


        Person p1 = new Person("kaj@mail.dk", "Kaj", "Testesen");
        Phone phone = new Phone(54667283, "This is Kaj's phone");
        p1.addPhone(phone);
        Hobby hobby = new Hobby("Turisme", "https://da.wikipedia.org/wiki/Turisme", "General", "Fritid");
        p1.addHobby(hobby);
        Address address = new Address("Lyngby Hovedgade 5", "Lyngby");
        p1.setAddress(address);
        CityInfo cityInfo = new CityInfo("2800", "Kongens Lyngby");
        address.setCityInfo(cityInfo);

        PersonFacade pf = PersonFacade.getPersonFacade(emf);
        pf.createPerson(new PersonDTO(p1));

    }

    public static void main(String[] args) {
        populate();
    }
}
