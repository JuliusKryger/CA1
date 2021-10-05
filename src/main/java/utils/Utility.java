/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dtos.*;

import java.util.*;

import com.google.gson.*;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tha
 */
public class Utility {

    private static final Gson gson = new GsonBuilder().create();

    public static boolean ValidatePerson(Person p) {
        return p.getEmail() != null || p.getFirstName() != null || p.getLastName() != null;
    }

    public static boolean ValidatePersonDto(PersonDTO p) {
        System.out.println("Validation: " + p.getEmail() != null || p.getFirstName() != null || p.getLastName() != null);
        return p.getEmail() != null || p.getFirstName() != null || p.getLastName() != null;
    }

    public static void printAllProperties() {
        Properties prop = System.getProperties();
        Set<Object> keySet = prop.keySet();
        for (Object obj : keySet) {
            System.out.println("System Property: {"
                    + obj.toString() + ","
                    + System.getProperty(obj.toString()) + "}");
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
    }

    public static List convertList(Class<?> type, List list) {
        List l = new ArrayList();
        for (Object p : list) {
            if (type == Phone.class) l.add(new Phone((PhoneDTO) p));
            if (type == PhoneDTO.class) l.add(new PhoneDTO((Phone) p));
            if (type == Hobby.class) l.add(new Hobby((HobbyDTO) p));
            if (type == HobbyDTO.class) l.add(new HobbyDTO((Hobby) p));
            if (type == CityInfo.class) l.add(new CityInfoDTO((CityInfo) p));
            if (type == PersonDTO.class) l.add(new PersonDTO((Person) p));
        }
        return l;
    }

}
