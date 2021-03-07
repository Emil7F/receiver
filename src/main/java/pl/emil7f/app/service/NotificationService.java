package pl.emil7f.app.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.emil7f.app.model.Notification;

@Service
public class NotificationService {
    private RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    public void getAndPrintStudentNotification(Notification notification) {
        if (notification != null) {
            System.out.println(notification.getEmail() + " " + notification.getTitle() + " " + notification.getBody());
        }
    }
}
