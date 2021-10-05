package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
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
    public String editAddress(@PathParam("id") int id){
        String street = scanner.next();
        String addInfo = scanner.next();
        String zip = scanner.next();
        String city = scanner.next();
        PersonDTO person = FACADE.getPersonByID(id);
        PersonDTO personDTO = FACADE.editAddressForPerson(street, addInfo, zip, city, person);
        return GSON.toJson(personDTO);
    }

    @Path("/phone/{id}")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public String editPhone(@PathParam("id") int id){
        //PersonDTO pdto = GSON.fromJson()
        //int id = scanner.nextInt();
        int phoneNumber = scanner.nextInt();
        String description = scanner.next();
        PersonDTO person = FACADE.getPersonByID(id);
        PersonDTO personDTO = FACADE.editPersonPhone(phoneNumber, description,person);
        return GSON.toJson(personDTO);
    }
    @Path("/hobby")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public String addHobby(){
        int id = scanner.nextInt();
        String hobbyName = scanner.next();
        PersonDTO personDTO = FACADE.addHobbiesToPerson(id, hobbyName);
        return GSON.toJson(personDTO);
    }


}
