package BaseRequestConfigs;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class DefaultConfigs {

    private static RequestSpecification specification;


    public static PreemptiveBasicAuthScheme setUpBaseAuth(String DEFAULT_USERNAME, String DEFAULT_PASSWORD){
        PreemptiveBasicAuthScheme basicAuth = new PreemptiveBasicAuthScheme();
        basicAuth.setUserName(DEFAULT_USERNAME);
        basicAuth.setPassword(DEFAULT_PASSWORD);

        return basicAuth;
    }

    public static RequestSpecification createSpec(String baseURI, String username, String password) {

        return specification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseURI)
                .setAuth( DefaultConfigs.setUpBaseAuth(username, password))
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }


}
