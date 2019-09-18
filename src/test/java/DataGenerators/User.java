package DataGenerators;

public class User {
    private String username;
    private String password;
    private boolean userEnabled;
    private String roleName;
    private String userID;



    public User(String username, String password, boolean userEnabled, String roleName, String userID) {
        this.username = username;
        this.password = password;
        this.userEnabled = userEnabled;
        this.roleName = roleName;
        this.userID = userID;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
