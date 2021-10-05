package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import facades.IPersonFacade;
import facades.PersonFacade;
import utils.EMF_Creator;


import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/person")
public class PersonResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final IPersonFacade iPersonFacade = null;
    private static final PersonFacade personFacade = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


/* @Operation(summary = "Get person by ID"),
    tags = {"person"},
    responses = {
        @ApiResponse(
                content = @content(mediaType = "aaplication/json",
                schema = @Schema(implementation = PersonDTO.class))
        ),
        @ApiResponse(responseCode = "200", description = "Found person"),
        @ApiResponse(responseCode = "400", description = "No persons found")})*/

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id")int id){
        PersonDTO personDTO = personFacade.getPersonByID(id);
        return GSON.toJson(personDTO);
    }

    @Path("/createPerson")
    @POST
    @Produces("application/json")
    public String getCreatePerson (String person){
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newPersonDTO = personFacade.createPerson(personDTO);

        return GSON.toJson(newPersonDTO);
    }


}