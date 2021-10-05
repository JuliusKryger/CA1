package rest;

import dtos.PersonDTO;
import entities.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
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

        c1 = new CityInfo("2800", "Lyngby");

        a1 = new Address("Nørgaardsvej", "28", c1);

        ph1 = new Phone(8888888, "phone");

        p1 = new Person("Harry", "Potter", "harrypotter@gmail.com");

        try {
            em.getTransaction().begin();
            //Address
            em.persist(c1);
            a1.setCityInfo(c1);
            em.persist(a1);
            //phone
            em.persist(ph1);
            ph1.setPerson(p1);
            em.merge(ph1);
            //
            em.persist(p1);

            em.getTransaction().commit();
            em.close();
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
    void getPersonById() throws Exception {
        String expectedName = p1.getFirstName();

        given()
                .pathParam("id", 1)
                .contentType("application/json")
                .get("/person/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("Harry", equalToIgnoringCase(expectedName));

    }

    @Test
    void getCreatePerson() {
    }

    @Test
    void getupdatePerson() {
    }
}