package com.appops.bit.kafka.springbootkafkaconsumerexample.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@EnableKafkaStreams
@Configuration
public class KafkaConfiguration {

	private static final String BOOTSTRAP_SERVERS = "localhost:9094";

	private static final String CERT_DIR = "C:\\opt\\kcl\\kafka\\cert\\";
	private static final String JASS_CONFIG = CERT_DIR + "kafka_server_jaas.conf";

	private static final String TRUSTSTORE_JKS = CERT_DIR + "kafka.truststore.jks";
	private static final String TRUSTSTORE_PASSWORD = "password";
	private static final String SASL_PROTOCOL = "SASL_SSL";
	private static final String SCRAM_SHA_512 = "SCRAM-SHA-512";
	private final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
	private final String CONSUMER_GROUP = "demo-consumer-group";

	private final String prodJaasCfg = String.format(jaasTemplate, "demouser", "secret");

	static {
		System.setProperty("java.security.auth.login.config", JASS_CONFIG);
	}
	private final String STREAM_APPLICATION_ID = "stream-app";

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	KafkaStreamsConfiguration kStreamsConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put("application.id", STREAM_APPLICATION_ID);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());		
		props.put("sasl.mechanism", SCRAM_SHA_512);
		props.put("sasl.jaas.config", prodJaasCfg);
		props.put("security.protocol", SASL_PROTOCOL);
		props.put("ssl.truststore.location", TRUSTSTORE_JKS);
		props.put("ssl.truststore.password", TRUSTSTORE_PASSWORD);
		props.put("ssl.endpoint.identification.algorithm", "");

		return new KafkaStreamsConfiguration(props);
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
		config.put("ssl.truststore.password", TRUSTSTORE_PASSWORD);
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
