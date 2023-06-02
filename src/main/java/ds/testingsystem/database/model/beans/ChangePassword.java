package ds.testingsystem.database.model.beans;

import java.time.LocalDateTime;

public class ChangePassword {
    private int userId;
    private String verCode;
    private String newPassword;
    private LocalDateTime localDateTime;

    public ChangePassword() {
    }

    public ChangePassword(int userId, String verCode, String newPassword) {
        this.userId = userId;
        this.verCode = verCode;
        this.newPassword = newPassword;
    }

    public ChangePassword(int userId, String verCode, String newPassword, LocalDateTime localDateTime) {
        this.userId = userId;
        this.verCode = verCode;
        this.newPassword = newPassword;
        this.localDateTime = localDateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
