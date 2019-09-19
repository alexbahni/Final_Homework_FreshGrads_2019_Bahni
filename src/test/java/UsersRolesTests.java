import BaseRequestConfigs.DefaultConfigs;
import DataGenerators.UserDataGenerator;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class UsersRolesTests {
    UserDataGenerator userDataGenerator;

    static String baseURI;
    static RequestSpecification specification;
    Response response;

    @BeforeMethod
    public static void initialSetUp() {
        Properties propertiesFile = new Properties();
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/configs/config.properties");
            propertiesFile.load(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("We could not find the properties file! Please check if the address is correct.");
        } catch (IOException e) {
            System.out.println("We cold not load the properties file!");
        }


        baseURI = propertiesFile.getProperty("BASE_URI");
        String defaultUsername = propertiesFile.getProperty("DEFAULT_USERNAME");
        String defaultPassword = propertiesFile.getProperty("DEFAULT_PASSWORD");

        specification = DefaultConfigs.createSpec(baseURI, defaultUsername, defaultPassword);
    }



    @Test
    public void checkOwnerAdminRights() {
        userDataGenerator = new UserDataGenerator();

        // ----- GIVEN I HAVE ACCESS TO PETCLINIC API AND I HAVE AN ADMIN WITH OWNER_ROLE ----- //
        response = given()
                .spec(specification)
                .body(userDataGenerator.createUser("OWNER_ADMIN"))
                .when()
                .post("users");
        // ----- WHEN I MAKE A CALL TO /vets ENDPOINT I SHOULD GET 401 CODE
        given()
                .spec(DefaultConfigs.createSpec(baseURI, response.body().jsonPath().getString("username"), response.body().jsonPath().getString("password"))).log().all()
                .when()
                .get("vets")
                .then().log().ifStatusCodeIsEqualTo(401);

    }


}
