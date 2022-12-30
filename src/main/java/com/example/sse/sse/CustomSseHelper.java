package com.example.sse.sse;

import com.example.sse.notification.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomSseHelper {

    private final SseRepository sseRepository;

    public SseInfoHolder createSseInfoHolder(String memberUUID, String lastEventId) {

        SseInfoHolder infoHolder = new SseInfoHolder(memberUUID, lastEventId);
        String emitterId = infoHolder.getEmitterId();

        if (lastEventId != null) {
            Map<String, Object> cache = sseRepository.findAllEventsByMemberUUID(lastEventId);

            if (!cache.isEmpty()) {
                infoHolder.setData(cache.get(lastEventId));
                emitterId = lastEventId;
            }
        }

        SseEmitter sseEmitter = sseRepository.saveSse(emitterId, infoHolder.getSseEmitter());
        sseRepository.saveEvent(emitterId, "initialization");
        setCallBackOption(sseEmitter, emitterId);

        return infoHolder;
    }


    private void setCallBackOption(SseEmitter sseEmitter, String emitterId) {
        sseEmitter.onCompletion(() ->
        {
            log.info("Async request is completed.");
            sseRepository.deleteEmitterById(emitterId);
        });

        sseEmitter.onTimeout(() -> {
            log.info("Async request is timed out. Emitter is removed.");
            sseRepository.deleteEmitterById(emitterId);
        });
    }

    public void send(SseInfoHolder infoHolder) {
        Object data = infoHolder.getData();
        String emitterId = infoHolder.getEmitterId();
        SseEmitter sseEmitter = infoHolder.getSseEmitter();

        try {
            log.info("sending data: {} ", data);
            sseEmitter.send(SseEmitter.event().id(emitterId).data(data));
        } catch (IOException e) {
            log.debug("event push failed. info : {} / " +  "error message : {}", emitterId, e.getMessage());
            sseRepository.deleteEmitterById(emitterId);

        }
    }

    public Map<String, SseEmitter> getEmittersByMemberUUID(String memberUUID) {
        return sseRepository.findAllSseByMemberUUID(memberUUID);
    }

    public void saveEventCache(String eventId, Object object) {
        sseRepository.saveEvent(eventId, object);
    }

}
