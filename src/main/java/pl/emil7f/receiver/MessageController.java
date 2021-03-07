package pl.emil7f.receiver;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
