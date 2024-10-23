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
import ru.t1.java.demo.model.dto.AccountMockDto;
import ru.t1.java.demo.service.AccountService;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaAccountConsumer {

  private final AccountService accountService;

  @KafkaListener(id = "t1-kafka-account-listener}",
      idIsGroup = false,
      topics = "${t1.kafka.topic.t1_demo_accounts}",
      containerFactory = "accountKafkaListenerContainerFactory")
  public void listener(@Payload List<AccountMockDto> messageList,
      Acknowledgment ack,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      @Header(KafkaHeaders.RECEIVED_KEY) String key) {
    log.debug("Account consumer: Обработка новых сообщений");

    try {
      accountService.saveMocks(messageList);
    } finally {
      ack.acknowledge();
    }

    log.debug("Account consumer: записи обработаны");
  }

}
