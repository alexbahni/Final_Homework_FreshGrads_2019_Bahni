import BaseRequestConfigs.DefaultConfigs;
import DataTransferObject.UserDTO;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import sun.nio.cs.US_ASCII;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DBInteractionsTests extends SetUpTests{
    Response response;
    UserDTO userDTO;
    Faker faker;

    private static final Logger LOGGER = LogManager.getLogger(DBInteractionsTests.class.getName());

    @Test
    @Parameters({"userType", "endpoint"})
    public void checkPostInteractionWith_DB(String userType, String endpoint) {
        LOGGER.info("-----------------------------------------------------------------------");
        LOGGER.info("CHECK POST INTERACTION WITH DATABASE");
        LOGGER.info("-----------------------------------------------------------------------");
        // ----- GENERATING DATA ----- //
        faker = new Faker();
        userDTO = new UserDTO();
        userDTO.setUsername(faker.name().username());
        userDTO.setPassword(faker.name().firstName() + faker.number().randomNumber());
        userDTO.setEnabled(true);
        userDTO.setRoles("[{\"id\":\"" + String.valueOf(faker.number().randomNumber()) + "\", \"name\":\"" + userType + "\"}]");

        // ----- GIVEN I HAVE ACCESS TO PETCLINIC API AND I HAVE AN ADMIN WITH OWNER_ROLE ----- //
        LOGGER.info("CREATING A CONNECTION TO PETCLINIC_SECURED API");
        LOGGER.info("CREATING AN USER WITH " + userType + " ROLE");

        response = given()
                .spec(specification)
                .body(userDTO)
                .when()
                .post("users");

        DBConnection dbConnection = new DBConnection();
        String userCreated = dbConnection.makeDBConnection(properties.getDB_URL()
                , properties.getDB_Username()
                , properties.getDB_Password()
                , "SELECT * FROM users WHERE username=\"" + response.body().jsonPath().getString("username") + "\"");

        Assert.assertEquals(userCreated, response.body().jsonPath().getString("username"));
    }

}
