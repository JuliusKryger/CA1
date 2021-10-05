package rest;

import entities.*;
import facades.PersonFacade;
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
/*
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;

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

        //Used for test methods
        p1 = new Person("Harry", "Potter", "harrypotter@gmail.com");
        CityInfo c1 = new CityInfo("9999", "Testhavnen");
        Address a1 = new Address("4 Privet Drive", "hv", c1);
        Phone ph1 = new Phone(88888888, "phone");
        a1.setCityInfo(new CityInfo("9999", "Testhavnen"));
        p1.setAddress(a1);
        p1.addPhone(ph1);

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(p1);
        //em.persist(r2);

        em.close();

    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    void getPersonById() throws Exception{
        String expectedName = p1.getFirstName();

        given()
                .pathParam("id", 1)
                .contentType("application/json")
                .get("/person/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstName", equalToIgnoringCase(expectedName));

    }

    @Test
    void getCreatePerson() {
    }

    @Test
    void getupdatePerson() {
    }

 */
}