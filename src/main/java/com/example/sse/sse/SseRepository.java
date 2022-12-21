package com.example.sse.sse;

import com.example.sse.notification.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

public interface SseRepository<T> {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    T saveEvent(String eventId, T event);

    Map<String, SseEmitter> findAllSseByMemberId(String memberId);

    Map<String, T> findAllEventsByMemberId(String memberId);

    void deleteById(String emitterId);

    void deleteAllByMemberId(String memberId);
}
