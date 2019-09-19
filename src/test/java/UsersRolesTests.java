import BaseRequestConfigs.DefaultConfigs;
import DataTransferObject.UserDTO;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import sun.nio.cs.US_ASCII;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class UsersRolesTests {
    static RequestSpecification specification;
    Response response;

    UserDTO userDTO;
    Faker faker;

    private static final Logger LOGGER = LogManager.getLogger(UsersRolesTests.class.getName());

    @BeforeClass
    public static void initialSetUp() {
        Properties propertiesFile = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
            propertiesFile.load(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("We could not find the properties file! Please check if the address is correct.");
        } catch (IOException e) {
            System.out.println("We cold not load the properties file!");
        }

        specification = DefaultConfigs.createSpec(propertiesFile.getProperty("DEFAULT_USERNAME"), propertiesFile.getProperty("DEFAULT_PASSWORD"));
        LOGGER.info("GETTING STARTED BY COLLECTING THE DEFAULT USERNAME AND PASSWORD");
    }


    @Test
    @Parameters({"userType", "endpoint"})
    public void checkOwnerAdminRights(String userType, String endpoint) {
        faker = new Faker();
        userDTO = new UserDTO();
        userDTO.setUsername(faker.name().username());
        userDTO.setPassword(faker.name().firstName() + faker.number().randomNumber());
        userDTO.setEnabled(true);
        userDTO.setRoles("[{\"id\":\"" + String.valueOf(faker.number().randomNumber()) + "\", \"name\":\"" + userType + "\"}]");

        // ----- GIVEN I HAVE ACCESS TO PETCLINIC API AND I HAVE AN ADMIN WITH OWNER_ROLE ----- //
        LOGGER.info("CREATING A CONNECTION TO PETCLINIC API");
        LOGGER.info("CREATING AN USER WITH " + userType +" ROLE");

        response = given()
                .spec(specification)
                .body(userDTO)
                .when()
                .post("users");

        // ----- WHEN I MAKE A CALL TO /vets ENDPOINT I SHOULD GET 401 CODE
        LOGGER.info("WITH THE NEW CREATED ADMIN (" + userType + " ROLE) I TRY TO ACCESS THE " + endpoint + " ENDPOINT");

        given()
                .spec(DefaultConfigs.createSpec(response.body().jsonPath().getString("username"), response.body().jsonPath().getString("password")))
                .when()
                .get(endpoint)
                .then().log().ifStatusCodeIsEqualTo(401);

    }


}
