package com.example.sse.notification;

import com.example.sse.sse.SseRepository;
import com.example.sse.sse.SseRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.net.http.HttpConnectTimeoutException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SseRepository sseRepository;

    static final Long TIME_OUT = 10 * 1000L;

    public void sendNotification(Notification notification) {

        String memberUUID = getMemberUUID(notification);
        Notification savedNotification = notificationRepository.save(notification);
        Map<String, SseEmitter> emitters = sseRepository.findAllSseByMemberUUID(memberUUID);

        emitters.forEach((key, sseEmitter) -> {
            sseRepository.saveEvent(key, savedNotification);
            send(sseEmitter, key, key, notification);
        });

    }

    public SseEmitter subscribe(String memberUUID, String lastEventId) {
        String emitterId = buildEmitterId(memberUUID);
        String emitterIdCache = emitterId;
        SseEmitter sseEmitter = sseRepository.saveSse(emitterId, new SseEmitter(TIME_OUT));
        sseRepository.saveEvent(emitterId, "initialization");

        Object data = "connection made for user [" + memberUUID + "] is made";

//        if (lastEventId != null) {
//            Map<String, Object> cache = sseRepository.findAllEventsByMemberUUID(lastEventId);
//
//            if (!cache.isEmpty()) {
//                data = cache.get(lastEventId);
//                emitterId = lastEventId;
//            }
//        }

        // send dummy
        send(sseEmitter, emitterId, emitterId, data);

        sseEmitter.onCompletion(() -> {

            sseRepository.deleteEmitterById(emitterIdCache);
            log.info("message has been sent. emitter removed.");

        });

        sseEmitter.onTimeout(() -> {
            sseRepository.deleteEmitterById(emitterIdCache);
            log.info("connection of [" + memberUUID + "] is timed-out and emitter is deleted");
        });


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
            log.info("sending data: {} ", data);
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
