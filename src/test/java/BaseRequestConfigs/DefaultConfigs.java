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

    public static RequestSpecification createSpec(String username, String password) {
        Properties propertiesFile = new Properties();
        FileInputStream fileInputStream;
        String baseURI;


        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
            propertiesFile.load(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("We could not find the properties file! Please check if the address is correct.");
        } catch (IOException e) {
            System.out.println("We cold not load the properties file!");
        }



        return specification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(propertiesFile.getProperty("BASE_URI"))
                .setAuth( DefaultConfigs.setUpBaseAuth(username, password))
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }


}
