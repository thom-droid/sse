package com.example.sse.sse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class SseRepositoryImpl<T> implements SseRepository<T> {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, T> eventCaches = new ConcurrentHashMap<>();

    @Override
    public SseEmitter saveSse(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public T saveEvent(String emitterId, T event) {
        eventCaches.put(emitterId, event);
        return event;
    }

    @Override
    public Map<String, SseEmitter> findAllSseByMemberUUID(String memberUUID) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberUUID))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, T> findAllEventsByMemberUUID(String memberId) {
        return eventCaches.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteEmitterById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllEmittersByMemberUUID(String memberUUID) {
        emitters.forEach(
                (key, emitter)  -> {
                    if (key.startsWith(memberUUID)) {
                        emitters.remove(key);
                    }
                }
        );
    }


}
