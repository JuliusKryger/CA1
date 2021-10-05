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
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
    public PersonDTO createNewPerson(PersonDTO p) {
        return FACADE.createPerson(p);
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getPersonById(id), PersonDTO.class);
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        return GSON.toJson(FACADE.getAllPersons());
    }

    //TODO: Hmmm ... vi skal kunne vælge en specifik person ikke?
    @Path("/update/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonDTO updatePerson(@PathParam("id") int id, PersonDTO p) {
        return FACADE.updatePerson(p);
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editPerson(@PathParam("id") int id) {
        return "{\"result\":\"" + FACADE.deletePersonById(id) + "\"}";
    }

    /*
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String getCreatePerson(PersonDTO p) {
        PersonDTO personDTO = GSON.fromJson(p, PersonDTO.class);
        PersonDTO newPersonDTO = personFacade.createPerson(personDTO);

        return GSON.toJson(newPersonDTO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonDTO createNewPerson(PersonDTO p) {
        return GSON.toJson(personFacade.createPerson(p));
    }

    @Path("/id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") int id) {
        PersonDTO personDTO = personFacade.getPersonByID(id);
        return GSON.toJson(personDTO);
    }

    @Path("/deleteP/{id}")
    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    public String deletePerson(@PathParam("id") int id){
        boolean personDeleted = personFacade.deletePersonById(id);
        return GSON.toJson(personDeleted);
    }

    @Path("/createPerson/firstname")
    @POST
    @Produces("application/json")
    public String getFirstName(String firstname) {
        Person newPersonFirstName = new Person();
        String personFirstName = newPersonFirstName.getFirstName();

        return GSON.toJson(personFirstName);
    }

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
