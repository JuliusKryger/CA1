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


@Path("/person")
public class PersonResource {
    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("/status")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String status() {
        return "{\"msg\":\"API is up and running.\"}";
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //virker
    public String createNewPerson(String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newPerson = FACADE.createPerson(personDTO);

        return GSON.toJson(newPerson);
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //virker
    public String getPersonById(@PathParam("id") int id) {
        PersonDTO personDTO  = FACADE.getPersonById(id);
        return GSON.toJson(personDTO);
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //virker
    public String getAllPersons() {
        PersonsDTO persons = FACADE.getAllPersons();
        return GSON.toJson(persons);
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //virker
    public String deletePerson(@PathParam("id") int id) {
        return "{\"result\":\"" + FACADE.deletePersonById(id) + "\"}";
    }

    //TODO: Hmmm ... vi skal kunne vælge en specifik person ikke?
    @Path("/update/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //Virker
    public String updatePerson(@PathParam("id") int id, String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personDTO.setId(id);
        PersonDTO updatedPerson = FACADE.updatePerson(personDTO);
        return GSON.toJson(updatedPerson);
    }

/*
    @Path("getziplist/{zip}")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public String getZipList(@PathParam("zip") String zip){
        PersonsDTO zipList = personFacade.getPersonListByZip(zip);
        return GSON.toJson(zipList);
    }
 */
}
