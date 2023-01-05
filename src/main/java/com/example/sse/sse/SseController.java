package com.example.sse.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Controller
public class SseController {

    private ExecutorService nonblockingService = Executors.newCachedThreadPool();

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handleSse() {
        SseEmitter sseEmitter = new SseEmitter(10000L);
        log.info("{} : message : {} ", Thread.currentThread().getName(), Math.random());
        nonblockingService.execute(() -> {
            try {
                sseEmitter.send("/sse" + " @ " + new Date());
                sseEmitter.complete();

            } catch (IOException e) {
                sseEmitter.completeWithError(e);

            }
        });

        return sseEmitter;

    }
}
