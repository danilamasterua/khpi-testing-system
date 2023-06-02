package ds.testingsystem.database.model.beans;

import ds.testingsystem.database.model.User;

import java.time.LocalDateTime;

public class UserTestPoints {
    private User user;
    private Double points;
    private LocalDateTime dateTime;

    public UserTestPoints() {}
    public UserTestPoints(User user, Double points) {
        this.user = user;
        this.points = points;
    }

    public UserTestPoints(User user, Double points, LocalDateTime dateTime) {
        this.user = user;
        this.points = points;
        this.dateTime = dateTime;
    }

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public Double getPoints() {return points;}
    public void setPoints(Double points) {this.points = points;}

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
