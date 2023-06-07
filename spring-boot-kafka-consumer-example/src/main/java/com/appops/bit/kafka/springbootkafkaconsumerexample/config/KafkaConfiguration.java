package com.appops.bit.kafka.springbootkafkaconsumerexample.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConfiguration {


	private static final String BOOTSTRAP_SERVERS = "localhost:9093";
	
	private static final String CERT_DIR= "C:\\opt\\poc\\kafka-sasl-ssl\\certs\\";
	private static final String JASS_CONFIG = CERT_DIR+"jaas.conf";
	
	private static final String TRUSTSTORE_JKS = CERT_DIR+"truststore.jks";
	private static final String PASSWORD="xxxxx";
	private static final String SASL_PROTOCOL = "SASL_SSL";
	private static final String SCRAM_SHA_512 = "SCRAM-SHA-512";
	private final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
	private final String CONSUMER_GROUP="email-notification-consumer";

	private final String prodJaasCfg = String.format(jaasTemplate, "kafka-topic-user", "kafka-topic-password");
	
static {
	System.setProperty("java.security.auth.login.config",JASS_CONFIG);   
}
	@Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		config.put("sasl.mechanism", SCRAM_SHA_512); 
		config.put("sasl.jaas.config", prodJaasCfg); 
		config.put("security.protocol", SASL_PROTOCOL); 
		config.put("ssl.truststore.location", TRUSTSTORE_JKS); 
		config.put("ssl.truststore.password", PASSWORD); 
		config.put("ssl.endpoint.identification.algorithm", ""); 
		
        return new DefaultKafkaConsumerFactory<>(config);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
    
}
