package com.appops.bit.kafka.springbootkafkaconsumerexample.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	
	  @KafkaListener(topics = "EMAIL_NOTIFICATION_TOPIC", group = "email-notification-consumer") public void
	  consume(String message) { System.out.println("Consumed message: " + message);
	  }
}
