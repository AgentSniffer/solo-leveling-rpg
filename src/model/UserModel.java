package model;

public class UserModel {

    public String email, hashedPassword; // Hashed password for security

    public UserModel(String newEmail, String newPass) {
        email = newEmail;
        hashedPassword = newPass;
    }
}