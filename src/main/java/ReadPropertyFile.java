import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class ReadPropertyFile {
    String result ="";
    InputStream inputStream;

    private String baseURI;
    private String defaultUsername;
    private String defaultPassword;
    private String DB_URL;
    private String DB_Driver;
    private String DB_Username;
    private String DB_Password;

    public void getPropertiesValues() throws IOException {

        try {
            Properties propertiesFile = new Properties();
            String propertiesFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

            if (inputStream != null) {
                propertiesFile.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propertiesFileName + "' not found in the classpath!");
            }

            Date time = new Date(System.currentTimeMillis());

            baseURI = propertiesFile.getProperty("BASE_URI");
            defaultUsername = propertiesFile.getProperty("DEFAULT_USERNAME");
            defaultPassword = propertiesFile.getProperty("DEFAULT_PASSWORD");
            DB_URL = propertiesFile.getProperty("DATABASE_URL");
            DB_Driver = propertiesFile.getProperty("DATABASE_MYSQL_DRIVER");
            DB_Username = propertiesFile.getProperty("DATABASE_USERNAME");
            DB_Password = propertiesFile.getProperty("DATABASE_PASSWORD");


        } catch (Exception e) {
            System.out.println("Exception : " + e);
        } finally {
            inputStream.close();
        }

    }

    public String getBaseURI() {
        return baseURI;
    }

    public String getDB_Driver() {
        return DB_Driver;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getDB_Username() {
        return DB_Username;
    }

    public String getDB_Password() {
        return DB_Password;
    }
}
