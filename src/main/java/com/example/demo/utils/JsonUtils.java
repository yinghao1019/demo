package com.example.demo.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import exception.InternalServerErrorException;

public class JsonUtils {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		MAPPER.registerModule(new JavaTimeModule());
	}

	private JsonUtils() {
	}

	public static String toJsonString(Object o) {
		try {
			return MAPPER.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}

	public static Map<String, Object> toMap(String jsonString) {
		try {
			return MAPPER.readValue(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}
	
	public static <T> T toObject(String jsonString, Class<T> targetClazz) {
		try {
			return MAPPER.readValue(jsonString, targetClazz);
		} catch (JsonProcessingException e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}

	public static <T> List<T> toList(String jsonString, Class<T> object) {
		TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
		};
		try {
			return MAPPER.readValue(jsonString, typeReference);
		} catch (JsonProcessingException e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}
}
