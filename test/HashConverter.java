import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashConverter {

    public static String sha256Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashedBytes.length; i++) {
                sb.append(String.format("%02x", hashedBytes[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found");
        }
    }

    public static void main(String[] args) {
        String test = "test = test";
        String hashed = sha256Hash(test);

        System.out.println("Original text: " + test);
        System.out.println("SHA-256 hash: " + hashed);
    }
}
