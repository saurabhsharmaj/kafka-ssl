package com.appops.bit.kafka.springbootkafkaproducerexample.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appops.bit.kafka.springbootkafkaproducerexample.model.BankTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("kafka")
public class UserResource {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	KafkaTemplate<String, String> bankTransactionTemplate;

	private static final String DEMO_TOPIC = "demo-topic";

	private static final String OUTPUT_TOPIC = "output-topic";

	private static final String BANK_TRANSACTION_TOPIC = "bank-transactions";

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@GetMapping("/publish/{topic}/{name}")
	public String post(@PathVariable("topic") final String topic, @PathVariable("name") final String name) {
		if (topic.equalsIgnoreCase("1")) {
			kafkaTemplate.send(DEMO_TOPIC, name);
		} else if (topic.equalsIgnoreCase("2")) {
			kafkaTemplate.send(OUTPUT_TOPIC, name);
		}

		return "Published successfully";
	}

	@PostMapping("/publish/banktransaction")
	public String pushBankTransaction(@RequestBody BankTransaction bankTransaction) throws JsonProcessingException {
		BankTransaction tran1 = BankTransaction.builder().balanceId(bankTransaction.getBalanceId()).time(new Date())
				.amount(bankTransaction.getAmount()).build();
		bankTransactionTemplate.send(BANK_TRANSACTION_TOPIC, toJson(tran1));
		return "Published successfully";
	}

	private static String toJson(BankTransaction bankTransaction) throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(bankTransaction);
	}

}
