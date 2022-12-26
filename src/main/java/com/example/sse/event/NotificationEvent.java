package com.example.sse.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationEvent<T> extends ApplicationEvent {

    private T event;

    public NotificationEvent(Object source, T event) {
        super(source);
        this.event = event;
    }
}
