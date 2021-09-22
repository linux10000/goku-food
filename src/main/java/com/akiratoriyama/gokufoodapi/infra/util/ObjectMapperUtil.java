package com.akiratoriyama.gokufoodapi.infra.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class ObjectMapperUtil {
	private static ObjectMapper objectMapper;
	
	private ObjectMapperUtil() {
		throw new UnsupportedOperationException();
	}

	
	public static ObjectMapper getObjectMapper() {
		if ( objectMapper == null )
			objectMapper = createOm();
		
		return objectMapper;
	}
	
	private static ObjectMapper createOm() {
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
		om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		om.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
		om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		om.configure(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature(), true);
		om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		om.registerModule(new ParameterNamesModule());
		om.registerModule(new Jdk8Module());
		
		om.setSerializationInclusion(Include.NON_NULL);	
		
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
			@Override
			public void serialize(OffsetDateTime value, JsonGenerator gen,
					SerializerProvider serializers) throws IOException {					
				gen.writeString(DateUtil.offsetDateTimeToString(value));
			}
		});
		
		javaTimeModule.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
			@Override
			public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException {
				return DateUtil.stringToOffsetDateTime(p.getValueAsString());
			}
		});
		
		javaTimeModule.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
			@Override
			public void serialize(LocalDate value, JsonGenerator gen,
					SerializerProvider serializers) throws IOException {					
				gen.writeString(DateUtil.localDateToString(value));
			}
		});
		
		javaTimeModule.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
			@Override
			public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException {
				return DateUtil.stringToLocalDate(p.getValueAsString());
			}
		});
		
		javaTimeModule.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			@Override
			public void serialize(LocalDateTime value, JsonGenerator gen,
					SerializerProvider serializers) throws IOException {					
				gen.writeString(DateUtil.localDateTimeToString(value));
			}
		});
		
		javaTimeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException {
				return DateUtil.stringToLocalDateTime(p.getValueAsString());
			}
		});
		
		om.registerModule(javaTimeModule);
		
		return om;
	}
}
