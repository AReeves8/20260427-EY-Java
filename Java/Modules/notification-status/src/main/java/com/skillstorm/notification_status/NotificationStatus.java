package main.java.com.skillstorm.notification_status;

public enum NotificationStatus {

    PENDING, 
    QUEUED, 
    SENT, 
    FAILED, 
    RETRYING;

    // can add methods to your enums
    public boolean canRetry() {
        return this == FAILED || this == RETRYING;
    }

    public boolean hasFinished() {
        return this == FAILED || this == SENT;
    }

    public NotificationStatus onSuccess() {
        return SENT;
    }

    public NotificationStatus onFailure() {
        return this == RETRYING ? FAILED : RETRYING;
    }
}
