package com.spring.Journal.Service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Test
    void testSendEmail() {
        // Arrange: mock JavaMailSender
        JavaMailSender mailSender = mock(JavaMailSender.class);
        EmailService emailService = new EmailService(mailSender);

        String to = "xyz@gmail.com";
        String cc = "asd@gmail.com";
        String subject = "Just Testing";
        String message = "Hello, it seems email service is working!!";

        // Act: call the service
        emailService.sendMail(to, cc, subject, message);

        // Assert: verify send was called with correct message
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(to, sentMessage.getTo()[0]);
        assertEquals(cc, sentMessage.getCc()[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(message, sentMessage.getText());
    }
}
