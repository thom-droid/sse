package com.example.sse.slice;

import com.example.sse.helper.testdb.TestDBInstance;
import com.example.sse.member.Member;
import com.example.sse.notification.Notification;
import com.example.sse.notification.NotificationRepository;
import com.example.sse.notification.NotificationService;
import com.example.sse.sse.SseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SseRepository sseRepository;

    @Autowired
    private TestDBInstance testDBInstance;

    Notification notification;

    @BeforeEach
    public void setNotification() {
        Member receiver = testDBInstance.getMemberById(1L);
        notification = Notification.of(Notification.Type.NEW_REPLY, receiver, "api url");
    }

    @Test
    public void givenNotification_whenServiceInvoked_thenDoesNotThrowIOException() {

        assertDoesNotThrow(() -> notificationService.sendNotification(notification));
    }

    @Test
    void givenMemberUUID_whenSubscribed_thenSseEmitterReturned() {

        Member subscriber = testDBInstance.getMemberById(1L);
        String memberUUID = subscriber.getUuid();
        String lastEventId = "";

        assertDoesNotThrow(() -> notificationService.subscribe(memberUUID, lastEventId));
    }
}
