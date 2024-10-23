package ru.t1.java.demo.configuration;

import static java.util.Map.entry;
import static ru.t1.java.demo.util.KafkaConsumerUtil.defaultFactoryBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.annotation.ProducerEnabledCondition;
import ru.t1.java.demo.kafka.KafkaAccountProducer;
import ru.t1.java.demo.kafka.KafkaClientProducer;
import ru.t1.java.demo.kafka.KafkaExecutionTimeProducer;
import ru.t1.java.demo.kafka.KafkaTransactionProducer;
import ru.t1.java.demo.model.dto.AccountMockDto;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.model.dto.ExecutionTimeKafkaLogDto;
import ru.t1.java.demo.model.dto.TransactionMockDto;
import ru.t1.java.demo.util.KafkaConsumerUtil;
import ru.t1.java.demo.util.KafkaTemplateUtil;

// ToDo - Заменить mock dto на command содержащий enum команды, id, версию и dto без id и версии

@Slf4j
@Configuration
public class KafkaConfiguration {

  private final String clientTopic;
  private final String accountTopic;
  private final String transactionTopic;
  private final String metricTopic;
  private final Map<String, Object> customConsumerProperties;

  public KafkaConfiguration(
      @Value("${t1.kafka.topic.client_id_registered}") String clientTopic,
      @Value("${t1.kafka.topic.t1_demo_accounts}") String accountTopic,
      @Value("${t1.kafka.topic.t1_demo_transactions}") String transactionTopic,
      @Value("${t1.kafka.topic.t1_demo_metric_trace}") String metricTopic,
      @Value("${t1.kafka.session.timeout.ms:15000}") String sessionTimeout,
      @Value("${t1.kafka.max.partition.fetch.bytes:300000}") String maxPartitionFetchBytes,
      @Value("${t1.kafka.max.poll.interval.ms:3000}") String maxPollIntervalsMs) {
    this.clientTopic = clientTopic;
    this.accountTopic = accountTopic;
    this.transactionTopic = transactionTopic;
    this.metricTopic = metricTopic;
    customConsumerProperties = Map.ofEntries(
        entry(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout),
        entry(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes),
        entry(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalsMs)
    );
  }

  @Bean
  @ProducerEnabledCondition
  public KafkaExecutionTimeProducer<ExecutionTimeKafkaLogDto> executionTimeProducer(
      KafkaProperties kafkaProperties, ObjectMapper mapper) {
    KafkaTemplate<String, ExecutionTimeKafkaLogDto> template = KafkaTemplateUtil
        .defaultKafkaTemplate(kafkaProperties, mapper);
    template.setDefaultTopic(metricTopic);
    return new KafkaExecutionTimeProducer<>(template);
  }

  @Bean
  @ProducerEnabledCondition
  public KafkaClientProducer<Long, ClientDto> clientProducer(KafkaProperties kafkaProperties,
      ObjectMapper mapper) {
    KafkaTemplate<String, Long> clientIdTemplate = KafkaTemplateUtil.defaultKafkaTemplate(
        kafkaProperties, mapper);
    KafkaTemplate<String, ClientDto> clientTemplate = KafkaTemplateUtil.defaultKafkaTemplate(
        kafkaProperties, mapper);
    clientIdTemplate.setDefaultTopic(clientTopic);
    clientTemplate.setDefaultTopic(clientTopic);
    return new KafkaClientProducer<>(clientIdTemplate, clientTemplate);
  }

  @Bean
  @ProducerEnabledCondition
  public KafkaAccountProducer<AccountMockDto> accountProducer(KafkaProperties kafkaProperties,
      ObjectMapper mapper) {
    KafkaTemplate<String, AccountMockDto> template = KafkaTemplateUtil.defaultKafkaTemplate(
        kafkaProperties, mapper);
    template.setDefaultTopic(accountTopic);
    return new KafkaAccountProducer<>(template);
  }

  @Bean
  @ProducerEnabledCondition
  public KafkaTransactionProducer<TransactionMockDto> transactionProducer(
      KafkaProperties kafkaProperties, ObjectMapper mapper) {
    KafkaTemplate<String, TransactionMockDto> template = KafkaTemplateUtil.defaultKafkaTemplate(
        kafkaProperties, mapper);
    template.setDefaultTopic(transactionTopic);
    return new KafkaTransactionProducer<>(template);
  }

  @Bean
  public ConsumerFactory<String, ClientDto> clientConsumerListenerFactory(
      KafkaProperties kafkaProperties, ObjectMapper mapper) {
    return KafkaConsumerUtil.defaultConsumerFactory(kafkaProperties,
        customConsumerProperties,
        mapper,
        "ru.t1.java.demo.model.dto.ClientDto");
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, ClientDto> clientKafkaListenerContainerFactory(
      ConsumerFactory<String, ClientDto> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, ClientDto> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    defaultFactoryBuilder(consumerFactory, factory);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, AccountMockDto> accountConsumerListenerFactory(
      KafkaProperties kafkaProperties, ObjectMapper mapper) {
    return KafkaConsumerUtil.defaultConsumerFactory(kafkaProperties,
        customConsumerProperties,
        mapper,
        "ru.t1.java.demo.model.dto.AccountMockDto");
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, AccountMockDto> accountKafkaListenerContainerFactory(
      ConsumerFactory<String, AccountMockDto> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, AccountMockDto> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    defaultFactoryBuilder(consumerFactory, factory);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, TransactionMockDto> transactionConsumerListenerFactory(
      KafkaProperties kafkaProperties, ObjectMapper mapper) {
    return KafkaConsumerUtil.defaultConsumerFactory(kafkaProperties,
        customConsumerProperties,
        mapper,
        "ru.t1.java.demo.model.dto.TransactionMockDto");
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, TransactionMockDto> transactionKafkaListenerContainerFactory(
      ConsumerFactory<String, TransactionMockDto> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, TransactionMockDto> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    defaultFactoryBuilder(consumerFactory, factory);
    return factory;
  }
}
