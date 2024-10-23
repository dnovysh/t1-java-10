package ru.t1.java.demo.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageDeserializer<T> extends JsonDeserializer<T> {

  public MessageDeserializer(ObjectMapper mapper) {
    super(mapper);
  }

  private static String getMessage(byte[] data) {
    return new String(data, StandardCharsets.UTF_8);
  }

  @Override
  public T deserialize(String topic, Headers headers, byte[] data) {
    try {
      return super.deserialize(topic, headers, data);
    } catch (Exception e) {
      log.warn("Произошла ошибка во время десериализации сообщения {}", getMessage(data), e);
      return null;
    }
  }

  @Override
  public T deserialize(String topic, byte[] data) {
    try {
      return super.deserialize(topic, data);
    } catch (Exception e) {
      log.warn("Произошла ошибка во время десериализации сообщения {}", getMessage(data), e);
      return null;
    }
  }
}
