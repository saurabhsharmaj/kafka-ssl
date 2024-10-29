package com.appops.bit.kafka.springbootkafkaproducerexample.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.appops.bit.kafka.springbootkafkaproducerexample.model.BankTransaction;

@Configuration
public class KakfaConfiguration {


	private static final String BOOTSTRAP_SERVERS = "localhost:9094";

	private static final String CERT_DIR= "C:\\opt\\kcl\\kafka\\cert\\";
	private static final String JASS_CONFIG = CERT_DIR+"kafka_server_jaas.conf";

	private static final String TRUSTSTORE_JKS = CERT_DIR+"kafka.truststore.jks";
	private static final String TRUSTSTORE_PASSWORD="password";
	private static final String SASL_PROTOCOL = "SASL_SSL";
	private static final String SCRAM_SHA_512 = "SCRAM-SHA-512";
	private final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
	private final String CONSUMER_GROUP="demo-consumer-group";

	private final String prodJaasCfg = String.format(jaasTemplate, "demouser", "secret");

	static {
		System.setProperty("java.security.auth.login.config",JASS_CONFIG);   
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		config.put("sasl.mechanism", SCRAM_SHA_512); 
		config.put("sasl.jaas.config", prodJaasCfg); 
		config.put("security.protocol", SASL_PROTOCOL); 
		config.put("ssl.truststore.location", TRUSTSTORE_JKS); 
		config.put("ssl.truststore.password", TRUSTSTORE_PASSWORD); 
		config.put("ssl.endpoint.identification.algorithm", ""); 

		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
		
	@Bean
	public  ProducerFactory<String, String> bankTransactionProducerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		config.put("sasl.mechanism", SCRAM_SHA_512); 
		config.put("sasl.jaas.config", prodJaasCfg); 
		config.put("security.protocol", SASL_PROTOCOL); 
		config.put("ssl.truststore.location", TRUSTSTORE_JKS); 
		config.put("ssl.truststore.password", TRUSTSTORE_PASSWORD); 
		config.put("ssl.endpoint.identification.algorithm", ""); 

		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, String> bankTransactionTemplate() {
		return new KafkaTemplate<>(bankTransactionProducerFactory());
	}
	
}
