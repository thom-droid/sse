package com.example.sse.sse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Repository
public class SseRepositoryImpl implements SseRepository {

    @Override
    public Map<String, SseEmitter> saveEmitter(String emitterId, SseEmitter sseEmitter) {
        return null;
    }

    @Override
    public Map saveEvent(String eventId, Object event) {
        return null;
    }

    @Override
    public Map<String, SseEmitter> findAllSseByMemberId(String memberId) {
        return null;
    }
}
