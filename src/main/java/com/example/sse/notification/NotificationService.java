package com.example.sse.notification;

import com.example.sse.sse.SseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SseRepository<Notification> sseRepository;

    static final Long TIME_OUT = 60 * 60 * 1000L;

    public void sendNotification(Notification notification) {

        String memberUUID = getMemberUUID(notification);
        Notification savedNotification = saveNotification(notification);
        Map<String, SseEmitter> emitters = sseRepository.findAllSseByMemberUUID(memberUUID);

        emitters.forEach((key, sseEmitter) -> {
            sseRepository.saveEvent(key, savedNotification);
            send(sseEmitter, key, key, notification);
        });

    }

    public SseEmitter subscribe(String memberUUID, String lastEventId) {
        String emitterId = buildEmitterId(memberUUID);
        SseEmitter sseEmitter = sseRepository.saveSse(emitterId, new SseEmitter(TIME_OUT));
        sseEmitter.onCompletion(() -> sseRepository.deleteEmitterById(emitterId));
        sseEmitter.onTimeout(() -> {
            sseRepository.deleteEmitterById(emitterId);
            log.info("connection of [" + memberUUID + "] is timed-out and emitter is deleted");
        });

        // send dummy
        send(sseEmitter, emitterId, emitterId, "connection made for user [" + memberUUID + "] is made");
        log.info("connection for user [" + memberUUID + "] is made");

        return sseEmitter;
    }

    private Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    private String getMemberUUID(Notification notification) {
        return notification.getReceiver().getUuid();
    }

    private void send(SseEmitter sseEmitter, String eventId, String emitterId, Object data) {

        try {
            sseEmitter.send(SseEmitter.event().id(eventId).data(data));
        } catch (IOException e) {
            log.debug("event push failed. info : {} / " +
                    "error message : {}", eventId, e.getMessage());
            sseRepository.deleteEmitterById(emitterId);

        }

    }

    private String buildEmitterId(String memberUUID) {
        return memberUUID + "-" + System.currentTimeMillis();
    }
}
