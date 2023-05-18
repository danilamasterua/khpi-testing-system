package ds.testingsystem.database.model.beans;

import ds.testingsystem.database.model.User;

public class UserTestPoints {
    private User user;
    private Double points;

    public UserTestPoints() {}
    public UserTestPoints(User user, Double points) {
        this.user = user;
        this.points = points;
    }
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public Double getPoints() {return points;}
    public void setPoints(Double points) {this.points = points;}
}
