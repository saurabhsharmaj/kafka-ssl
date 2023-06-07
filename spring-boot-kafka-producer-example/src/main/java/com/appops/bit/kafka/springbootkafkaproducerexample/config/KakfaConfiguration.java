package com.appops.bit.kafka.springbootkafkaproducerexample.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KakfaConfiguration {


	private static final String BOOTSTRAP_SERVERS = "localhost:9093";

	private static final String CERT_DIR= "C:\\opt\\cert\\kafka\\";
	
	private static final String JASS_CONFIG = CERT_DIR+"jaas.conf";
	private static final String TRUSTSTORE_JKS = CERT_DIR+"truststore.jks";
	private static final String PASSWORD="xxxx";
	private static final String SASL_PROTOCOL = "SASL_SSL";
	private static final String SCRAM_SHA_512 = "SCRAM-SHA-512";
	private final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
	private final String prodJaasCfg = String.format(jaasTemplate, "kafka-topic-user", "kafka-topic-password");
	
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
		config.put("ssl.truststore.password", PASSWORD); 
		config.put("ssl.endpoint.identification.algorithm", ""); 

		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

}
