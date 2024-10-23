package ru.t1.java.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import ru.t1.java.demo.kafka.MessageDeserializer;

@Slf4j
public class KafkaConsumerUtil {

  public static <V> ConsumerFactory<String, V> defaultConsumerFactory(
      KafkaProperties kafkaProperties,
      Map<String, Object> customProperties,
      ObjectMapper mapper,
      String valueDefaultType) {
    var props = kafkaProperties.buildConsumerProperties(null);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
        MessageDeserializer.class.getName());
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, MessageDeserializer.class);
    props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, valueDefaultType);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    props.putAll(customProperties);

    var factory = new DefaultKafkaConsumerFactory<String, V>(props);
    factory.setKeyDeserializer(new StringDeserializer());
    factory.setValueDeserializer(new MessageDeserializer<>(mapper));
    return factory;
  }

  public static <T> void defaultFactoryBuilder(ConsumerFactory<String, T> consumerFactory,
      ConcurrentKafkaListenerContainerFactory<String, T> factory) {
    factory.setConsumerFactory(consumerFactory);
    factory.setBatchListener(true);
    factory.setConcurrency(1);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    factory.getContainerProperties().setPollTimeout(5000);
    factory.getContainerProperties().setMicrometerEnabled(true);
    factory.setCommonErrorHandler(errorHandler());
  }

  public static CommonErrorHandler errorHandler() {
    DefaultErrorHandler handler = new DefaultErrorHandler(new FixedBackOff(1000, 3));
    handler.addNotRetryableExceptions(IllegalStateException.class);
    handler.setRetryListeners((record, ex, deliveryAttempt) -> log.error(
        " RetryListeners message = {}, offset = {} deliveryAttempt = {}", ex.getMessage(),
        record.offset(), deliveryAttempt));
    return handler;
  }
}
