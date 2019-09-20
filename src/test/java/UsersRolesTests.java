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


public class UsersRolesTests extends SetUpTests{
    Response response;
    UserDTO userDTO;
    Faker faker;

    private static final Logger LOGGER = LogManager.getLogger(UsersRolesTests.class.getName());

    @Test
    @Parameters({"userType", "endpoint"})
    public void checkAdminRights(String userType, String endpoint) {
        LOGGER.info("-----------------------------------------------------------------------");
        LOGGER.info("CHECK ADMIN RIGHTS");
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

        // ----- WHEN I MAKE A CALL TO /vets ENDPOINT I SHOULD GET 401 CODE
        LOGGER.info("WITH THE NEW CREATED ADMIN (" + userType + " ROLE) I TRY TO ACCESS THE " + endpoint + " ENDPOINT");

        given()
                .spec(DefaultConfigs.createSpec(properties.getBaseURI()
                        , response.body().jsonPath().getString("username")
                        , response.body().jsonPath().getString("password")))
                .when()
                .get(endpoint)
                .then().log().ifStatusCodeIsEqualTo(401);

    }

}
