package com.example.sse.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface SseRepository {

    SseEmitter saveSse(String emitterId, SseEmitter sseEmitter);

    Object saveEvent(String eventId, Object event);

    Map<String, SseEmitter> findAllSseByMemberUUID(String memberUUID);

    Map<String, Object> findAllEventsByMemberUUID(String memberUUID);

    void deleteEmitterById(String emitterId);

    void deleteAllEmittersByMemberUUID(String memberUUID);
}
