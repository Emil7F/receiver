package pl.emil7f.app.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.emil7f.app.model.Notification;
import pl.emil7f.app.service.NotificationService;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;
    private NotificationService notificationService;

    public MessageController(RabbitTemplate rabbitTemplate, NotificationService notificationService) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "myQueue")
    public void listenerMessage(Notification notification) {
        if (notification != null) {
            notificationService.getAndPrintStudentNotification(notification);
        } else {
            System.out.println("Invalid type");
        }

    }

    @GetMapping("/notification")
    public ResponseEntity<Notification> receiveNotification() {
        Notification notification = rabbitTemplate
                .receiveAndConvert("myQueue", ParameterizedTypeReference.forType(Notification.class));
        if (notification != null) {
            System.out.println(notification.getEmail());
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.noContent().build();
        }

    }


}
