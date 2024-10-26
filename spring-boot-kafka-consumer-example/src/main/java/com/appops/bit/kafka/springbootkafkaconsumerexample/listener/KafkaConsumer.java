package com.appops.bit.kafka.springbootkafkaconsumerexample.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	
	@KafkaListener(topics = "demo-topic", groupId = "demo-consumer-group")
	public void consume(String message) {
		System.out.println("Consumed message: " + message);
	}

	@KafkaListener(topics = "output-topic", groupId = "output-topic-consumer")
	public void outputTopicconsume(String message) {
		System.out.println("Output topic Consumed message: " + message);
	}
}
