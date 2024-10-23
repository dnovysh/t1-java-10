package ru.t1.java.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import ru.t1.java.demo.util.KafkaTemplateUtil;

@Slf4j
@Configuration
public class KafkaConfiguration {

  private final String clientTopic;
  private final String accountTopic;
  private final String transactionTopic;
  private final String metricTopic;

  public KafkaConfiguration(@Value("${t1.kafka.consumer.group-id}") String groupId,
      @Value("${t1.kafka.topic.client_id_registered}") String clientTopic,
      @Value("${t1.kafka.topic.t1_demo_accounts}") String accountTopic,
      @Value("${t1.kafka.topic.t1_demo_transactions}") String transactionTopic,
      @Value("${t1.kafka.topic.t1_demo_metric_trace}") String metricTopic) {
    this.clientTopic = clientTopic;
    this.accountTopic = accountTopic;
    this.transactionTopic = transactionTopic;
    this.metricTopic = metricTopic;
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
}
