package ru.t1.java.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class KafkaTemplateUtil {

  public static <T> ProducerFactory<String, T> defaultProducerFactory(
      KafkaProperties kafkaProperties,
      ObjectMapper mapper) {
    var props = kafkaProperties.buildProducerProperties(null);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
    return new DefaultKafkaProducerFactory<>(props);
  }

  public static <T> KafkaTemplate<String, T> defaultKafkaTemplate(
      KafkaProperties kafkaProperties,
      ObjectMapper mapper) {
    return new KafkaTemplate<>(defaultProducerFactory(kafkaProperties, mapper));
  }
}
