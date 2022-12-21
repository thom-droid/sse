package com.example.sse.sse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class SseRepositoryImpl<T> implements SseRepository<T> {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, T> eventCaches = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public T saveEvent(String eventId, T event) {
        eventCaches.put(eventId, event);
        return event;
    }

    @Override
    public Map<String, SseEmitter> findAllSseByMemberId(String memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, T> findAllEventsByMemberId(String memberId) {
        return eventCaches.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllByMemberId(String memberId) {
        emitters.forEach(
                (key, emitter)  -> {
                    if (key.startsWith(memberId)) {
                        emitters.remove(key);
                    }
                }
        );
    }
}
