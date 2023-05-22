package edu.miu.orderService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RabbitMQSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendOrderConfirmation(String sender, List<String> receivers, String subject, String message, List<Map<String, String>> files) {
        MailPayload payload = new MailPayload(sender, receivers, subject, message, files);
        sendMessage(payload);
    }

    public void sendDeliveryConfirmation(String sender, List<String> receivers, String subject, String message, List<Map<String, String>> files) {
        MailPayload payload = new MailPayload(sender, receivers, subject, message, files);
        sendMessage(payload);
    }

    private void sendMessage(MailPayload payload) {
        // Serialize the payload as JSON
        String payloadJson;
        try {
            payloadJson = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert payload to JSON", e);
        }

        // Convert the payload to a message
        Message rabbitMessage = MessageBuilder.withBody(payloadJson.getBytes())
                .setContentType("application/json")
                .build();

        // Send the message to the mail-queue
        rabbitTemplate.send("mail-queue", rabbitMessage);
    }

    private static class MailPayload {
        private final String sender;
        private final List<String> receivers;
        private final String object;
        private final String message;
        private final List<Map<String, String>> files;

        public MailPayload(String sender, List<String> receivers, String object, String message, List<Map<String, String>> files) {
            this.sender = sender;
            this.receivers = receivers;
            this.object = object;
            this.message = message;
            this.files = files;
        }

        public String getSender() {
            return sender;
        }

        public List<String> getReceivers() {
            return receivers;
        }

        public String getObject() {
            return object;
        }

        public String getMessage() {
            return message;
        }

        public List<Map<String, String>> getFiles() {
            return files;
        }
    }
}
