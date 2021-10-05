package rest;

import dtos.PersonDTO;
import entities.*;
import io.restassured.http.ContentType;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PersonEditResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer(){
        ResourceConfig resourceConfig = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }

    @BeforeAll
    public static void setUpClass(){
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer(){
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp(){
        EntityManager em = emf.createEntityManager();
        Phone phone = new Phone(12383232, "privat");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(phone);
        CityInfo cityInfo = new CityInfo("9776", "Broby");
        Address address = new Address("Milevejen", "7", cityInfo);
        Hobby hobby = new Hobby("Kiggeri", "wiki.dk", "enpersons hobby", "indendørs");
        List <Hobby> hobbyList = new ArrayList<>();
        hobbyList.add(hobby);

        p1 = new Person("BOB", "ANDERSEN", "BOB@AND.DK");
        p1.setAddress(address);
        p1.setPhones(phoneList);
        p1.setHobbies(hobbyList);

        p2 = new Person("ALICE", "BENDTSEN", "ALICE@BEN.DK");
        p2.setAddress(address);
        p1.setPhones(phoneList);
        p2.setHobbies(hobbyList);
        try{
            em.getTransaction().begin();
                em.createNamedQuery("Person.deleteAllRows", Person.class).executeUpdate();
                em.createNamedQuery("Address.deleteAllRows", Address.class).executeUpdate();
                em.persist(p1);
                em.persist(p2);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }

    }
    @Test
    void testServerIsRunning(){
        given().when().get("/personedit").then().statusCode(200);
    }

    @Test
    void updatePersonTest(){
        PersonDTO personDTO = new PersonDTO(p1);
        personDTO.setFirstName("Ib");
        personDTO.setLastName("Jensen");

        given()
                .contentType(ContentType.JSON)
                .body(personDTO)
                .when().put("personedit/basis/"+personDTO.getId())
                .then()
                .body("firstName", equalTo("Ib"))
                .body("lastName", equalTo("Jense"))
                .body("id", equalTo((int)personDTO.getId()));
    }

}
