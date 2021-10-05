package rest;

import dtos.PersonDTO;
import entities.*;
import facades.PersonFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;

import java.awt.*;
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
import entities.Person;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;

class PersonResourceTest {
    Address a1;
    Person p1;
    CityInfo c1;
    Phone ph1;

    Address a2;
    Person p2;
    CityInfo c2;
    Phone ph2;

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();

            c1 = new CityInfo("2800", "Lyngby");
            c2 = new CityInfo("2100", "København V");

            a1 = new Address("Nørgaardsvej", "28", c1);
            a2 = new Address("Sønder blvd", "18", c2);

            ph1 = new Phone(88888888, "phone");
            ph2 = new Phone(11111111, "phone");

            p1 = new Person("Harry", "Potter", "harrypotter@gmail.com");
            p2 = new Person("Ron", "Weasley", "ronweasley@gmail.com");

            //person1
            em.persist(c1);
            em.persist(a1);
            a1.setCityInfo(c1);
            em.merge(a1);

            em.persist(p1);
            p1.setAddress(a1);
            em.merge(p1);

            p1.addPhone(ph1);
            p2.addPhone(ph2);


            //person2
            em.persist(c2);
            em.persist(a2);
            a2.setCityInfo(c2);
            em.merge(a2);

            em.persist(p2);
            p2.setAddress(a2);
            em.merge(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }


    @Test
    void status() {
    }

    @Test
    void createNewPerson() {
    }

    @Test
    void getPersonById() throws Exception {
        System.out.println("Searching for ID: " + p1.getId());
        String expectedName = p1.getFirstName();
        given()
                .pathParam("id", p1.getId())
                .contentType("application/json")
                .get("/person/{id}")
                .then()
                .assertThat()
                .statusCode(200);
        //.body("John", equalToIgnoringCase(expectedName));
    }

    @Test
    void getAllPersons() {
    }

    @Test
    void updatePerson() {
    }

    @Test
    void editPerson() {
    }
}