package model;

public class UserModel {

    public String email, hashedPassword, salt; // Hashed password for security and salt for salting

    public UserModel(String newEmail, String newPass, String newSalt) {
        email = newEmail;
        hashedPassword = newPass;
        salt = newSalt;
    }
}
