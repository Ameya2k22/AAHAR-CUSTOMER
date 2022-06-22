package com.example.enduser.Models;

public class Notification {
    String notificationBy, notificationType, notificationAt, notificationId;

    public Notification() {
    }

    public Notification(String notificationBy, String notificationType, String notificationAt) {
        this.notificationBy = notificationBy;
        this.notificationType = notificationType;
        this.notificationAt = notificationAt;
    }

    public String getNotificationBy() {
        return notificationBy;
    }

    public void setNotificationBy(String notificationBy) {
        this.notificationBy = notificationBy;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationAt() {
        return notificationAt;
    }

    public void setNotificationAt(String notificationAt) {
        this.notificationAt = notificationAt;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
