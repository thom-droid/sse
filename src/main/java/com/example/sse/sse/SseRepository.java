package com.example.sse.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface SseRepository {

    void saveAllEvents(List<SseEmitter> sseEmitters);

    void removeAllEvent(String userId);

    List<SseEmitter> getAllSsesByUserId(String userId);
}
