package edu.miu.orderService.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.queue.send}")
    private String sendQueueName;

    @Value("${rabbitmq.url}")
    private String rabbitMQUrl;

    @Value("${rabbitmq.port}")
    private int rabbitMQPort;

    @Value("${rabbitmq.user}")
    private String rabbitMQUser;

    @Value("${rabbitmq.password}")
    private String rabbitMQPassword;

    @Bean
    public Queue sendQueue() {
        return new Queue(sendQueueName, true);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMQUrl);
        connectionFactory.setPort(rabbitMQPort);
        connectionFactory.setUsername(rabbitMQUser);
        connectionFactory.setPassword(rabbitMQPassword);
        return connectionFactory;
    }
}