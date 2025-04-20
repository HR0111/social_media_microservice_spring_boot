package org.hemant.linkedin.userservice.utils;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // hash a password for the first time
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainTextPassword , String hashedPassword){
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

}
