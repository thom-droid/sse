package com.example.sse.sse;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class SseRepositoryImpl implements SseRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCaches = new ConcurrentHashMap<>();

    @Override
    public SseEmitter saveSse(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public Object saveEvent(String emitterId, Object event) {
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
    public Map<String, Object> findAllEventsByMemberUUID(String memberId) {
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
