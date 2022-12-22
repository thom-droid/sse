package com.example.sse.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.awt.*;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/event-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeEvent(
            String memberUUID,
            @RequestHeader(required = false, value = "Last-Event-ID") String lastEventId) {

        return notificationService.subscribe(memberUUID, lastEventId);

    }
}
