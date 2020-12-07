package com.example.demo.data;

import java.util.Map;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapToJsonConverter implements AttributeConverter<Map<String, Object>, String> {
	private ObjectMapper jackson;

	public MapToJsonConverter() {
		this.jackson = new ObjectMapper();
	}

	@Override
	public String convertToDatabaseColumn(Map<String, Object> details) {
		// use jackson for marshalling
		try {
			return this.jackson.writeValueAsString(details);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> convertToEntityAttribute(String json) {
		// use jackson for unmarshalling
		try {
			return this.jackson.readValue(json, Map.class); // type safety warning acceptable
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}