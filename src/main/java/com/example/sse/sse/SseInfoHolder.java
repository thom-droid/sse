package com.example.sse.sse;

import com.example.sse.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@AllArgsConstructor
@Getter
public class SseInfoHolder {

    private static final long TIME_OUT = 60 * 1000L;

    private final String memberUUID;
    private final String lastEventId;
    private String emitterId;
    private SseEmitter sseEmitter;

    @Setter
    private Object data;

    private String buildEmitterId(String memberUUID) {
        return memberUUID + "-" + System.currentTimeMillis();
    }

    public SseInfoHolder(String memberUUID, String lastEventId) {
        this.memberUUID = memberUUID;
        this.lastEventId = lastEventId;
        this.emitterId = buildEmitterId(memberUUID);
        this.data = "connection made for user [" + memberUUID + "] is made";
        this.sseEmitter = new SseEmitter(TIME_OUT);
    }

    public SseInfoHolder(String emitterId, Object data, SseEmitter sseEmitter) {
        this(null, null);
        this.emitterId = emitterId;
        this.data = data;
        this.sseEmitter = sseEmitter;
    }


}
