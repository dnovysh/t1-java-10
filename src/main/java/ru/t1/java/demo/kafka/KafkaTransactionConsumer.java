package ru.t1.java.demo.kafka;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.dto.TransactionMockDto;
import ru.t1.java.demo.service.TransactionService;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {

  private final TransactionService transactionService;

  @KafkaListener(id = "t1-kafka-transaction-listener}",
      idIsGroup = false,
      topics = "${t1.kafka.topic.t1_demo_transactions}",
      containerFactory = "transactionKafkaListenerContainerFactory")
  public void listener(@Payload List<TransactionMockDto> messageList,
      Acknowledgment ack,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      @Header(KafkaHeaders.RECEIVED_KEY) String key) {
    log.debug("Transaction consumer: Обработка новых сообщений");

    try {
      transactionService.saveMocks(messageList);
    } finally {
      ack.acknowledge();
    }

    log.debug("Transaction consumer: записи обработаны");
  }

}
