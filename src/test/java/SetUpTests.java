import BaseRequestConfigs.DefaultConfigs;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class SetUpTests {
    RequestSpecification specification;
    ReadPropertyFile properties;

    @BeforeMethod
    public void initialSetUp() {

        properties = new ReadPropertyFile();
        try {
            properties.getPropertiesValues();
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }

        specification = DefaultConfigs.createSpec(properties.getBaseURI(), properties.getDefaultUsername(), properties.getDefaultPassword());
    }
}
