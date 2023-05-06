package ds.testingsystem.web.controllers;

import ds.testingsystem.database.UserDAO;
import ds.testingsystem.database.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;

public class UserController {
    public static boolean checkUserPassword(String login, String password) throws SQLException{
        User user = UserDAO.getUserInfo(login);
        String encryptedPassword = DigestUtils.sha256Hex(password);
        return user.getPassword().equals(encryptedPassword);
    }
    public static User getUserDataFromDB(String login) throws SQLException {
        User user = UserDAO.getUserInfo(login);
        return user;
    }
}
