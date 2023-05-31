package ds.testingsystem.web.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ibm.icu.text.Transliterator;
import ds.testingsystem.database.GroupDAO;
import ds.testingsystem.database.TestDAO;
import ds.testingsystem.database.UserDAO;
import ds.testingsystem.database.model.Group;
import ds.testingsystem.database.model.User;
import ds.testingsystem.database.model.beans.ChangePassword;
import ds.testingsystem.services.GeneratePassword;
import ds.testingsystem.services.Sender;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
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
    public static void updateUser(User user){UserDAO.updateUser(user);}
    public static void blockUser(int userId){UserDAO.blockUser(userId);}
    public static HashMap<Integer, User> getBlockedUsers(){return UserDAO.getBlockedUsers();}
    public static void unblockUser(int userId){UserDAO.unblockUser(userId);}
    public static int prepareChangePassword(String jsonData){
        Gson g = new Gson();
        JsonObject o = g.fromJson(jsonData, JsonObject.class);
        int userId = o.get("userId").getAsInt();
        String oldPassword = o.get("oldPassword").getAsString();
        String newPassword = o.get("newPassword").getAsString();
        User user = UserDAO.getUserById(userId);
        String encryptedOldPassword = DigestUtils.sha256Hex(oldPassword);
        if(user.getPassword().equals(encryptedOldPassword)){
            String encryptedNewPassword = DigestUtils.sha256Hex(newPassword);
            String verCode = GeneratePassword.genPass();
            String encryptedVerCode = DigestUtils.sha256Hex(verCode);
            ChangePassword chgPwd = new ChangePassword(userId, encryptedVerCode, encryptedNewPassword);
            if(UserDAO.prepareChangePassword(chgPwd)==200){
                Sender sender = new Sender("danmas.pp.ua@gmail.com", "opffixxoixybdlwy");
                sender.send("DS Testing System: Change password","Reply, if you don`t change password!\nVerification code: "+verCode, "danmas.pp.ua", user.getEmail());
                return 200;
            } else {
                return 500;
            }
        }
        return 401;
    }
    public static int changePassword(String verCode, int userId){
        ChangePassword cp = UserDAO.getPreparedChangePassword(userId);
        String encryptedInpVerCode = DigestUtils.sha256Hex(verCode);
        Duration duration = Duration.between(cp.getLocalDateTime(), LocalDateTime.now());
        long minutes = duration.toMinutes();
        if(encryptedInpVerCode.equals(cp.getVerCode())&&minutes<=15){
            return UserDAO.setNewPassword(userId, cp.getNewPassword());
        }
        return 401;
    }
}
