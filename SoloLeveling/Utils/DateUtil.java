package SoloLeveling.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getCurrentTime() {
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        return now.format(formatTime);
    }

    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return now.format(formatDate);   
    }

    public static void main(String[] args) {
        System.out.println(getCurrentTime());
        System.out.println(getCurrentDate());
    }
}