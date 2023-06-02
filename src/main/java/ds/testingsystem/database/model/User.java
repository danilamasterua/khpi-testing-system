package ds.testingsystem.database.model;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private int roleId;
    private String email;
    private int groupId;

    public User() {
    }

    public User(String firstName, String lastName, String login, String password, int roleId, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
    }

    public User(int userId, String firstName, String lastName, String login, String password, int roleId, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
    }

    public User(String firstName, String lastName, String login, String password, int roleId, String email, int groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
        this.groupId = groupId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
