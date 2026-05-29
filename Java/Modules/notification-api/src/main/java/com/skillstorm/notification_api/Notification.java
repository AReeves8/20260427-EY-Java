package main.java.com.skillstorm.notification_api;

import main.java.com.skillstorm.notification_status.NotificationStatus;
import main.java.com.skillstorm.notification_status.Priority;

public interface Notification {

    Priority getPriority();
    NotificationStatus getStatus();
    String getChannel();
    void send(String recipient, String message);

}
