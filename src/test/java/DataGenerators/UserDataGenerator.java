package DataGenerators;

import com.github.javafaker.Faker;

public class UserDataGenerator {
    User user;
    UserJSON userJSON;
    Faker faker;

    public String createUser(String userType) {
        user = new User();
        userJSON = new UserJSON();
        faker = new Faker();

        user.setUsername(faker.name().username());
        user.setPassword(faker.name().firstName() + faker.number().randomNumber());
        user.setUserEnabled(true);
        user.setUserID(String.valueOf(faker.number().randomNumber()));
        user.setRoleName(userType);

        return userJSON.createUserJSON(
                user.getUsername(),
                user.getPassword(),
                user.getUserID(),
                user.getRoleName(),
                user.isUserEnabled());
    }

}
