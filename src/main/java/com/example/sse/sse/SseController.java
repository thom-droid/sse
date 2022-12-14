package com.example.sse.sse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class SseController {

    private ExecutorService nonblockingService = Executors.newCachedThreadPool();

    @GetMapping("/sse")
    public SseEmitter handleSse() {
        SseEmitter sseEmitter = new SseEmitter();

        nonblockingService.execute(()-> {
            try{
                sseEmitter.send("/sse" + " @ " + new Date());
                sseEmitter.complete();

            } catch (IOException e) {
                sseEmitter.completeWithError(e);

            }
        });

        return sseEmitter;

    }
}
