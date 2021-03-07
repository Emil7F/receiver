package pl.emil7f.receiver;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.emil7f.notification.Notification;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/message")
    public String receiveMessage() {
        Object message = rabbitTemplate.receiveAndConvert("myQueue");
        if (message != null) {
            return "Message downloaded from RabbitMQ: " + message.toString();
        } else {
            return "Queue is empty";
        }
    }

//    @RabbitListener(queues = "myQueue")
//    public void listenerMessage(String message) {
//        System.out.println(message);
//    }

    @GetMapping("/notification")
    public ResponseEntity<Notification> receiveNotification() {
        Object notification = rabbitTemplate.receiveAndConvert("myQueue");
        if (notification instanceof Notification) {
            return ResponseEntity.ok((Notification) notification);
        }else {
            return ResponseEntity.noContent().build();
        }
    }


}
