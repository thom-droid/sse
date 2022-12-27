package com.example.sse.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.awt.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/event-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeEvent(
            @RequestParam("member-uuid") String memberUUID,
            @RequestHeader(required = false, value = "Last-Event-ID") String lastEventId) {

        if (memberUUID.isEmpty()) {
            return null;
        }

        if (lastEventId != null) {
            log.info("last event id: {}", lastEventId);
        }

        return notificationService.subscribe(memberUUID, lastEventId);

    }
}
