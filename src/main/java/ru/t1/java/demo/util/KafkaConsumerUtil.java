package ru.t1.java.demo.util;

import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
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
import ru.t1.java.demo.model.dto.ClientDto;

@Slf4j
public class KafkaConsumerUtil {

  public static <V> ConsumerFactory<String, V> defaultConsumerFactory(
      KafkaProperties kafkaProperties, ObjectMapper mapper, String typeMapping) {
    var props = kafkaProperties.buildConsumerProperties(null);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
        MessageDeserializer.class.getName());
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, MessageDeserializer.class);

    props.put(TYPE_MAPPINGS, "ru.demo.model.StringValue:ru.demo.model.StringValue");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3_000);

    var factory = new DefaultKafkaConsumerFactory<String, V>(props);
    factory.setKeyDeserializer(new StringDeserializer());
    factory.setValueDeserializer(new MessageDeserializer<>(mapper));
    return factory;

    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "ru.t1.java.demo.model.dto.ClientDto");
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
    props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalsMs);


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
    handler.setRetryListeners((record, ex, deliveryAttempt) -> {
      log.error(" RetryListeners message = {}, offset = {} deliveryAttempt = {}", ex.getMessage(),
          record.offset(), deliveryAttempt);
    });
    return handler;
  }
}
