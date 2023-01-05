package com.example.sse.event;

import com.example.sse.notification.Notification;
import com.example.sse.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@EnableAsync
@RequiredArgsConstructor
@Component
public class NotificationEventListener {

    private final NotificationService notificationService;

    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener
    public void handleNotificationPush(NotificationEvent<Notification> event) {

        Notification notification = event.getEvent();

        log.info("notification is now being sent. receiver : {} , message : {}", notification.getReceiver().getId(), notification.getMessage());
        notificationService.sendNotification(notification);
    }


}
