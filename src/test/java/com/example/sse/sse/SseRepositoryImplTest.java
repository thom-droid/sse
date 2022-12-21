package com.example.sse.sse;

import com.example.sse.member.Member;
import com.example.sse.notification.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SseRepositoryImplTest {

    private SseRepositoryImpl<Notification> sseRepository = new SseRepositoryImpl<>();

    private Member member;
    private final String url = "http:localhost:8080/sse";

    @BeforeEach
    public void setup() {
        member = Member.builder()
                .id(1L)
                .name("membeer1")
                .build();
    }

    @Test
    void save() throws Exception {
        String emitterId = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter = new SseEmitter();

        assertDoesNotThrow(() -> sseRepository.save(emitterId, sseEmitter));

    }

    @Test
    void saveEvent(){
        Notification notification = Notification.of(Notification.Type.NEW_REPLY, member, url);
        String eventId = member.getId() + "-" + LocalDateTime.now();

        assertDoesNotThrow(() -> sseRepository.saveEvent(eventId, notification));

    }

    @Test
    void findAllSseByMemberId() throws InterruptedException{

        String emitterId1 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter1 = new SseEmitter();
        Thread.sleep(100);

        String emitterId2 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter2 = new SseEmitter();
        Thread.sleep(100);

        String emitterId3 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter3 = new SseEmitter();
        Thread.sleep(100);

        String emitterId4 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter4 = new SseEmitter();

        sseRepository.save(emitterId1, sseEmitter1);
        sseRepository.save(emitterId2, sseEmitter2);
        sseRepository.save(emitterId3, sseEmitter3);
        sseRepository.save(emitterId4, sseEmitter4);

        String memberId = member.getId() + "-";

        Map<String, SseEmitter> map = sseRepository.findAllSseByMemberId(memberId);

        assertEquals(4, map.size());

        for (Map.Entry<String, SseEmitter> entry : map.entrySet()) {
            System.out.println("key " + entry.getKey() + " :  / value : " + entry.getValue());
        }

    }

    @Test
    void findAllEventsByMemberId() throws InterruptedException{
        String eventId1 = member.getId() + "-" + LocalDateTime.now();
        Notification notification1 = Notification.of(Notification.Type.NEW_APPLICATION, member, url);
        Thread.sleep(100);

        String eventId2 = member.getId() + "-" + LocalDateTime.now();
        Notification notification2 = Notification.of(Notification.Type.APPLICATION_ACCEPT, member, url);
        Thread.sleep(100);

        String eventId3 = member.getId() + "-" + LocalDateTime.now();
        Notification notification3 = Notification.of(Notification.Type.NEW_REPLY, member, url);

        sseRepository.saveEvent(eventId1, notification1);
        sseRepository.saveEvent(eventId2, notification2);
        sseRepository.saveEvent(eventId3, notification3);

        String memberId = member.getId() + "-";

        Map<String, Notification> map = sseRepository.findAllEventsByMemberId(memberId);

        assertEquals(3, map.size());

        for (Map.Entry<String, Notification> entry : map.entrySet()) {
            System.out.println("key " + entry.getKey() + " :  / value : " + entry.getValue());
        }
    }

    @Test
    void deleteById() {
        String emitterId1 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter1 = new SseEmitter();

        sseRepository.save(emitterId1, sseEmitter1);
        sseRepository.deleteById(emitterId1);

        Map<String, SseEmitter> map = sseRepository.findAllSseByMemberId(emitterId1);

        assertTrue(map.isEmpty());
    }

    @Test
    void deleteAllByMemberId() throws InterruptedException{

        String emitterId1 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter1 = new SseEmitter();
        Thread.sleep(100);

        String emitterId2 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter2 = new SseEmitter();
        Thread.sleep(100);

        String emitterId3 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter3 = new SseEmitter();
        Thread.sleep(100);

        String emitterId4 = member.getId() + "-" + LocalDateTime.now();
        SseEmitter sseEmitter4 = new SseEmitter();

        sseRepository.save(emitterId1, sseEmitter1);
        sseRepository.save(emitterId2, sseEmitter2);
        sseRepository.save(emitterId3, sseEmitter3);
        sseRepository.save(emitterId4, sseEmitter4);

        String memberId = member.getId() + "-";

        sseRepository.deleteAllByMemberId(memberId);

        Map<String, SseEmitter> map = sseRepository.findAllSseByMemberId(memberId);

        assertTrue(map.isEmpty());

    }
}