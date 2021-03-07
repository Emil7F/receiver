package pl.emil7f.receiver.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.emil7f.receiver.model.Notification;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "myQueue")
    public void listenerMessage(Notification notification) {
        if (notification != null) {
            System.out.println(notification.getEmail());
        } else {
            System.out.println("Invalid type");
        }

    }

    @GetMapping("/notification")
    public ResponseEntity<Notification> receiveNotification() {
        Notification notification = rabbitTemplate
                .receiveAndConvert("myQueue", ParameterizedTypeReference.forType(Notification.class));
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


}
