package com.example.sse.notification;

import com.example.sse.sse.CustomSseHelper;
import com.example.sse.sse.SseInfoHolder;
import com.example.sse.sse.SseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CustomSseHelper customSseHelper;


    public void sendNotification(Notification notification) {

        String memberUUID = getMemberUUID(notification);
        Notification savedNotification = notificationRepository.save(notification);

        Map<String, SseEmitter> emitters = customSseHelper.getEmittersByMemberUUID(memberUUID);

        emitters.forEach((key, sseEmitter) -> {
            SseInfoHolder sseInfoHolder = new SseInfoHolder(memberUUID, notification, sseEmitter);
            customSseHelper.saveEventCache(key, savedNotification);
            customSseHelper.send(sseInfoHolder);
        });

    }

    public SseEmitter subscribe(String memberUUID, String lastEventId) {

        SseInfoHolder sseInfoHolder = customSseHelper.createSseInfoHolder(memberUUID, lastEventId);
        customSseHelper.send(sseInfoHolder);

        return sseInfoHolder.getSseEmitter();
    }

    private String getMemberUUID(Notification notification) {
        return notification.getReceiver().getUuid();
    }

}
