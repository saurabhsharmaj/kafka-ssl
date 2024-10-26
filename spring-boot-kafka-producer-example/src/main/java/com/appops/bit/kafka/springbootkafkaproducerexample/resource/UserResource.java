package com.appops.bit.kafka.springbootkafkaproducerexample.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class UserResource {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String DEMO_TOPIC = "demo-topic";

	private static final String OUTPUT_TOPIC = "output-topic";

	@GetMapping("/publish/{topic}/{name}")
	public String post(@PathVariable("topic") final String topic, @PathVariable("name") final String name) {
		if (topic.equalsIgnoreCase("1")) {
			kafkaTemplate.send(DEMO_TOPIC, name);
		} else if (topic.equalsIgnoreCase("2")) {
			kafkaTemplate.send(OUTPUT_TOPIC, name);
		}

		return "Published successfully";
	}

}
