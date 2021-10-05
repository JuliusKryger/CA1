package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;
import facades.IPersonFacade;
import facades.PersonFacade;
import utils.EMF_Creator;


import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("person")
public class PersonResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade personFacade = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") int id) {
        PersonDTO personDTO = personFacade.getPersonByID(id);
        return GSON.toJson(personDTO);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String getCreatePerson(String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newPersonDTO = personFacade.createPerson(personDTO);

        return GSON.toJson(newPersonDTO);
    }

    @Path("/createPerson/firstname")
    @POST
    @Produces("application/json")
    public String getFirstName(String firstname) {
        Person newPersonFirstName = new Person();
        String personFirstName = newPersonFirstName.getFirstName();

        return GSON.toJson(personFirstName);
    }

    @Path("{id}")
    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    public String deletePerson(@PathParam("id") int id){
        boolean personDeleted = personFacade.deletePersonById(id);
        return GSON.toJson(personDeleted);
    }

    @Path("getziplist/{zip}")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public String getZipList(@PathParam("zip") String zip){
        PersonsDTO zipList = personFacade.getPersonListByZip(zip);
        return GSON.toJson(zipList);
    }
}
