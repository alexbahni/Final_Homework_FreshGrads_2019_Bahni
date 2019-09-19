package DataGenerators;

import DataTransferObject.UserDTO;
import com.github.javafaker.Faker;

public class UserDataGenerator {
    UserDTO userDTO;
    Faker faker;

    public UserDTO createUser(String userType) {
        faker = new Faker();
        userDTO = new UserDTO();

        userDTO.setUsername(faker.name().username());
        userDTO.setPassword(faker.name().firstName() + faker.number().randomNumber());
        userDTO.setEnabled(true);
        userDTO.setRoles("[{\"id\":\"" + String.valueOf(faker.number().randomNumber()) + "\", \"name\":\"" + userType + "\"}]");

        return userDTO;
    }

}
