package com.example.sse.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public class SseRepositoryImpl implements SseRepository {

    @Override
    public void saveAllEvents(List<SseEmitter> sseEmitters) {

    }

    @Override
    public void removeAllEvent(String userId) {

    }

    @Override
    public List<SseEmitter> getAllSsesByUserId(String userId) {
        return null;
    }
}
