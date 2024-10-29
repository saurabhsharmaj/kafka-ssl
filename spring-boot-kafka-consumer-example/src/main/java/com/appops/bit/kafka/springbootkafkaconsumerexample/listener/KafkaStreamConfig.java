package com.appops.bit.kafka.springbootkafkaconsumerexample.listener;

import java.util.Arrays;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {
	
		
//    @Bean
//    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
//        KStream<String, String> stream = streamsBuilder.stream("demo-topic");        
//        stream
//            .mapValues(value -> value.toUpperCase())
//            .to("output-topic");
//        
//        return stream;
//    }
    
    @Bean
    public KStream<String, String> kStreamCountWord(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("demo-topic");
       
       stream.flatMapValues((key, value) ->
                Arrays.asList(value.toLowerCase()
                    .split(" ")))
        .groupBy((key, value) -> value)
        .count(Materialized.with(Serdes.String(), Serdes.Long()))
        .toStream()
        .to("word-count-topic", Produced.with(Serdes.String(), Serdes.Long()));     	
    	
        return stream;
    }
}