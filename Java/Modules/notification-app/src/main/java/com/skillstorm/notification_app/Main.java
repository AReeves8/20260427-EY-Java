package main.java.com.skillstorm.notification_app;

import main.java.com.skillstorm.notification_api.Notification;
import main.java.com.skillstorm.notification_status.Priority;

public class Main {
    public static void main(String[] args) {

        Notification email = new EmailNotification(Priority.HIGH);

        // do some stuff to send an email

        System.out.println("About to send email: " + email + "\n");
        email.send("mmarcoux@skillstorm.com", "Hello, World!");
        System.out.println("\nAfter sending email: " + email);

    }
}
