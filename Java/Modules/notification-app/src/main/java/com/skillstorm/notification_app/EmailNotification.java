package main.java.com.skillstorm.notification_app;

import main.java.com.skillstorm.notification_api.Notification;
import main.java.com.skillstorm.notification_status.NotificationStatus;
import main.java.com.skillstorm.notification_status.Priority;

public class EmailNotification implements Notification {

    private final Priority priority;
    private NotificationStatus status;

    public EmailNotification(Priority priority) {
        this.priority = priority;
        this.status = NotificationStatus.PENDING;
    }

    public EmailNotification(Priority priority, NotificationStatus status) {
        this.priority = priority;
        this.status = status;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public NotificationStatus getStatus() {
        return status;
    }

    @Override
    public String getChannel() {
        return "EMAIL";
    }

    @Override
    public void send(String recipient, String message) {
        String email = "[EMAIL]\n\tTO: " + recipient + "\tMSG: " + message + "\tPRIO: " + priority.getLevel();
        System.out.println(email);
        status = status.onSuccess();
        System.out.println("\tUPDATED STATUS: " + status);
    }

    


}
