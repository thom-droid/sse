package com.example.sse.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface SseRepository<T> {

    SseEmitter saveSse(String emitterId, SseEmitter sseEmitter);

    T saveEvent(String eventId, T event);

    Map<String, SseEmitter> findAllSseByMemberUUID(String memberUUID);

    Map<String, T> findAllEventsByMemberUUID(String memberUUID);

    void deleteEmitterById(String emitterId);

    void deleteAllEmittersByMemberUUID(String memberUUID);
}
