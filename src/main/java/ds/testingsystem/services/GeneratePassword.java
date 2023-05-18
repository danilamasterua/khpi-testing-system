package ds.testingsystem.services;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class GeneratePassword {
    private static final String possibleSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&@";

    public static String genPass(){
        StringBuilder retString = new StringBuilder();
        Random random = new Random();
        for (int i=0; i<8; i++){
            retString.append(possibleSymbols.charAt(random.nextInt(possibleSymbols.length())));
        }
        return retString.toString();
    }
    public static String encryptPassword(String notEncryptedPassword){
        return DigestUtils.sha256Hex(notEncryptedPassword);
    }
}
