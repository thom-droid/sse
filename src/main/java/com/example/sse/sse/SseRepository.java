package com.example.sse.sse;

import com.example.sse.notification.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

public interface SseRepository<T> {

    Map<String, SseEmitter> saveEmitter(String emitterId, SseEmitter sseEmitter);

    Map<String, T> saveEvent(String eventId, T event);

    Map<String, SseEmitter> findAllSseByMemberId(String memberId);
}
