package com.appops.bit.kafka.springbootkafkaconsumerexample.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	
	@KafkaListener(topics = "demo-topic", groupId = "demo-consumer-group")
	public void consume(String message) {
		System.err.println("demo-topic# Consumed message: " + message);
	}

	@KafkaListener(topics = "output-topic", groupId = "output-topic-consumer")
	public void outputTopicconsume(String message) {
		System.err.println("output-topic# Output topic Consumed message: " + message);
	}
	
	@KafkaListener(topics = "word-count-topic", groupId = "output-topic-consumer")
	public void wordCountTopicconsume(String message) {
		System.out.println("word-count-topic# Output topic Consumed message: " + message);
	}
}
