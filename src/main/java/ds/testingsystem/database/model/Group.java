package ds.testingsystem.database.model;

import java.util.HashMap;

public class Group {
    private String name;
    private HashMap<Integer, User> users;

    public Group() {}
    public Group(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public HashMap<Integer, User> getUsers() {return users;}
    public void setUsers(HashMap<Integer, User> users) {this.users = users;}
}
