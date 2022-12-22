package com.example.sse.event;

import com.example.sse.notification.Notification;
import com.example.sse.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@RequiredArgsConstructor
@Component
public class NotificationEventListener {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void handleNotificationPush(NotificationEvent<Notification> event) {
        Notification notification = event.getEvent();
        notificationService.sendNotification(notification);
    }


}
