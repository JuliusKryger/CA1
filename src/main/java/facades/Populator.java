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

public class Populator {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static ArrayList phone= new ArrayList();
    private static ArrayList hobby= new ArrayList();
    private static Person p1 = new Person("Ole", "Sørensen", "oleelskerhunde@gmail.com");
    private static Person p2 = new Person("Bente", "Rasmussen", "Bentehvem@hotmail.com");
    private static Person p3 = new Person("Sofus", "Pedersen", "minlillemissekat@kat.dk");
    private static Phone ph1 = new Phone(12345678, "Private");
    private static Phone ph2 = new Phone(87654321, "Private");
    private static Phone ph3 = new Phone(88888888, "Private");
    private static CityInfo ci1 = new CityInfo("2850", "Nærum", );
    private static CityInfo ci2 = new CityInfo("2000", "Frederiksberg");
    private static CityInfo ci3 = new CityInfo("3520", "Farum");
    private static Address a1 = new Address("Satsvej", "1 th.", ci1);
    private static Address a2 = new Address("Blommevej", "2 tv.", ci2);
    private static Address a3 = new Address("Solsikkehaven", "34", ci3);
    private static Hobby h1 = new Hobby("golf", "www.golf.dk", "ballplay", "ooutdoors");
    private static Hobby h2 = new Hobby("softball", "www.softball.dk", "ballplay", "ooutdoors");
    private static Hobby h3 = new Hobby("tennis", "www.tennis.dk", "ballplay", "ooutdoors");

    public static void populate() {
        EntityManager em = emf.createEntityManager();
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);

        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));
        try {
            em.getTransaction().begin();
            //Creating our hobby Array.
            hobby.add(h1);
            hobby.add(h2);
            hobby.add(h3);


            //Creating our phone Array.
            phone.add(ph1);
            phone.add(ph2);
            phone.add(ph3);

            //Person 1
            em.persist(ci1);
            em.persist(a1);
            a1.setCityInfo(ci1);
            em.merge(a1);

            em.persist(p1);
            p1.setAddress(a1);
            em.merge(p1);

            em.persist(hobby);
            p1.setHobbies(hobby);
            em.merge(p1);

            em.persist(phone);
            p1.setHobbies(phone);
            em.merge(p1);

            //Person 2
            em.persist(ci2);
            em.persist(a2);
            a2.setCityInfo(ci2);
            em.merge(a2);

            em.persist(p2);
            p2.setAddress(a2);
            em.merge(p2);

            em.persist(hobby);
            p2.setHobbies(hobby);
            em.merge(p2);

            em.persist(hobby);
            p2.setHobbies(hobby);
            em.merge(p2);

            em.persist(phone);
            p2.setHobbies(phone);
            em.merge(p2);

            //Person 3
            em.persist(ci3);
            em.persist(a3);
            a3.setCityInfo(ci3);
            em.merge(a3);

            em.persist(p3);
            p3.setAddress(a3);
            em.merge(p3);

            em.persist(hobby);
            p3.setHobbies(hobby);
            em.merge(p3);

            em.persist(hobby);
            p3.setHobbies(hobby);
            em.merge(p3);

            em.persist(phone);
            p3.setHobbies(phone);
            em.merge(p3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    public static void main(String[] args) {
        populate();
    }
}
