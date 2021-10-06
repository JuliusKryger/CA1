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

    //virker, men ikke testet, men test skrevet
    @Path("/basis/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editBasis(@PathParam("id") int id, String person){
        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
        pdto.setId(id);
        PersonDTO personDTO = FACADE.updatePerson(pdto);
        return GSON.toJson(personDTO);
    }

    //virker, men ingen test
    @Path("/address/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editAddress(@PathParam("id") int id, String addr){
        AddressDTO addressDTO = GSON.fromJson(addr, AddressDTO.class);

        PersonDTO personDTO = FACADE.editAddressForPerson(id, addressDTO);

        return GSON.toJson(personDTO);
    }

    //virker, men ingen test
    @Path("/phone/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editPhone(@PathParam("id") int id, String phone){
        PhoneDTO phoneDTO = GSON.fromJson(phone, PhoneDTO.class);

        PersonDTO personDTO = FACADE.editPersonPhone(id, phoneDTO);
        return GSON.toJson(personDTO);
    }

    //den virker måske
    @Path("/hobby/add/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addHobby(@PathParam("id") int id, String person,String hobbyName ){
        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
        pdto.setId(id);
        PersonDTO personDTO = FACADE.addHobbiesToPerson(id, hobbyName);
        return GSON.toJson(personDTO);
    }


}
