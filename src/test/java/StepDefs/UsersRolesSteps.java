package StepDefs;

import DataGenerators.UserDataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Test;

public class UsersRolesSteps {
    RequestSpecification requestSpecification;
    Response response;

    String baseURI = "http://bhdtest.endava.com:8080/petclinicSecured/api/";
    String usersBasePath = "/users";
    String vetsBasePath = "/vets";

    UserDataGenerator userDataGenerator;


    // ----- CREATE OWNER ----- //

    @Test
    public void checkOwnerAdminRights() {
        userDataGenerator = new UserDataGenerator();

        // ----- GIVEN I HAVE ACCESS TO PETCLINIC API ----- //
        RestAssured.baseURI = baseURI;
        requestSpecification = RestAssured.given();

        // ----- AND I HAVE AN USER WITH OWNER_ADMIN ROLE ----- //
        response = requestSpecification.given()
                .contentType(ContentType.JSON)
                .auth()
                .basic("admin", "admin")
                .body(userDataGenerator.createUser("OWNER_ADMIN"))
                .when()
                .post(usersBasePath);


        // ----- WHEN I MAKE A CALL TO /VETS ENDPOINT WITH AN USER WITH OWNER_ADMIN ROLE ----- //
        // ----- THEN I SHOULD RECIEVE 401 - UNAUTHORIZED HTTP STATUS CODE ----- //
        String username = response.body().jsonPath().getString("username");
        String password = response.body().jsonPath().getString("password");


        response = requestSpecification.given()
                .auth()
                .basic(username, password)
                .when()
                .get(vetsBasePath);

        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        System.out.println(response.body().jsonPath().getString("exMessage"));
    }


}
