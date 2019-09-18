package DataGenerators;

public class UserJSON {

    public UserJSON() {
    }

    public String createUserJSON(String username, String password, String userID, String roleName, boolean userEnabled) {
        final String POST_PARAMS = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "  \"enabled\": " + userEnabled + ",\n" +
                "  \"roles\": [\n" +
                "    {\n" +
                "      \"id\": \"" + userID + "\",\n" +
                "      \"name\": \"" + roleName + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        return POST_PARAMS;
    }
}
