package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Address;
import entities.CityInfo;
import entities.Phone;
import facades.IPersonFacade;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Path("/personedit")
public class PersonEditResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactoryForTest();
    //private static final IPersonFacade I_PERSON_FACADE = null;
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Scanner scanner = new Scanner(System.in);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String changePersonInformation(){
        return "{\"msg\":\"Hi, here you can change the information for a person\"}";
    }

    //Test er skrevet men ikke kørt
    @Path("/basis/{id}")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public String editBasis(@PathParam("id") int id, String person){
        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
        pdto.setId(id);
        PersonDTO personDTO = FACADE.updatePerson(pdto);
        return GSON.toJson(personDTO);
    }

    //ikke testet, find ud af om der skal være en scanner
    @Path("/address({id}")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public String editAddress(@PathParam("id") int id, String person, String street, String addInfo, String zip, String city){
        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
        pdto.setId(id);
        /*CityInfo cityInfo = new CityInfo(zip, city);
        Address address =  new Address(street, addInfo, cityInfo);
        pdto.setAddress(new AddressDTO(address));*/
        PersonDTO personDTO = FACADE.editAddressForPerson(street, addInfo, zip, city, pdto);
        return GSON.toJson(personDTO);
    }

    @Path("/phone/{id}")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public String editPhone(@PathParam("id") int id, String person, int phoneNumber, String description){
        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
        pdto.setId(id);
        /*List<PhoneDTO> phoneList = new ArrayList<>();
        PhoneDTO phone = new PhoneDTO(new Phone(phoneNumber, description));
        phoneList.add(phone);
        pdto.setPhones(phoneList);*/
        PersonDTO personDTO = FACADE.editPersonPhone(phoneNumber, description,pdto);
        return GSON.toJson(personDTO);
    }
    @Path("/hobby/add/{id}")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public String addHobby(@PathParam("id") int id, String person,String hobbyName ){
        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
        pdto.setId(id);
        PersonDTO personDTO = FACADE.addHobbiesToPerson(id, hobbyName);
        return GSON.toJson(personDTO);
    }


}
