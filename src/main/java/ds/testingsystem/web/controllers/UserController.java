package ds.testingsystem.web.controllers;

import com.ibm.icu.text.Transliterator;
import ds.testingsystem.database.GroupDAO;
import ds.testingsystem.database.UserDAO;
import ds.testingsystem.database.model.Group;
import ds.testingsystem.database.model.User;
import ds.testingsystem.services.GeneratePassword;
import ds.testingsystem.services.Sender;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.HashMap;

public class UserController {
    public static boolean checkUserPassword(String login, String password) throws SQLException{
        User user = UserDAO.getUserInfo(login);
        String encryptedPassword = DigestUtils.sha256Hex(password);
        return user.getPassword().equals(encryptedPassword);
    }
    public static User getUserDataFromDB(String login) throws SQLException {
        return UserDAO.getUserInfo(login);
    }
    public static int createUserTeacher(String firstName, String lastName, String email){
        Transliterator t = Transliterator.getInstance("Russian-Latin/BGN");
        String password = GeneratePassword.genPass();
        String login = t.transliterate(firstName).toLowerCase()+"."+t.transliterate(lastName);
        User user = new User(firstName, lastName, login, GeneratePassword.encryptPassword(password), 2, email, 1);
        int res = UserDAO.addUser(user);
        if(res!=-1){
            Sender sender = new Sender("danmas.pp.ua@gmail.com", "opffixxoixybdlwy");
            sender.send("DS Testing System: Notification", "You're registered to use DS Testing System\nLogin: "+login+"\nPassword: "+password, "danmas.pp.ua", email);
        }
        return res;
    }
    public static HashMap<Integer, User> getTeachers(){
        return UserDAO.getTeachers();
    }
    public static int createUserStudent(String firstName, String lastName, String email, int gId){
        Transliterator t = Transliterator.getInstance("Russian-Latin/BGN");
        String password = GeneratePassword.genPass();
        String login = t.transliterate(firstName).toLowerCase()+"."+t.transliterate(lastName);
        User user = new User(firstName, lastName, login, GeneratePassword.encryptPassword(password), 3, email, gId);
        int res = UserDAO.addUser(user);
        if(res!=-1){
            Sender sender = new Sender("danmas.pp.ua@gmail.com", "opffixxoixybdlwy");
            sender.send("DS Testing System: Notification", "You're registered to use DS Testing System\nLogin: "+login+"\nPassword: "+password, "danmas.pp.ua", email);
        }
        return res;
    }
    public static int createEdGroup(Group g){
        return GroupDAO.insertGroup(g);
    }
    public static User getUserById(int userId){return UserDAO.getUserById(userId);}
}
