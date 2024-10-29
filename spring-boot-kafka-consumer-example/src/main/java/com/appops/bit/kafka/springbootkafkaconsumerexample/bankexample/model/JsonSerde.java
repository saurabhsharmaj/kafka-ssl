package com.appops.bit.kafka.springbootkafkaconsumerexample.bankexample.model;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class JsonSerde<T> implements Serde<T> {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Class<T> type;

    public JsonSerde(Class<T> type) {
        this.type = type;
    }

    @Override
    public Serializer<T> serializer() {
        return (topic, data) -> serialize(data);
    }

    @SneakyThrows
    private byte[] serialize(T data) {
        try {
			return OBJECT_MAPPER.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public Deserializer<T> deserializer() {
        return (topic, bytes) -> deserialize(bytes);
    }

    @SneakyThrows
    private T deserialize(byte[] bytes) {
        try {
			return OBJECT_MAPPER.readValue(bytes, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
}
