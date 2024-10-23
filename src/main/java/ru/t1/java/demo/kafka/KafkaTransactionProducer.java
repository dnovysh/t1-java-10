package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.model.dto.TransactionCreateDto;

@Slf4j
@RequiredArgsConstructor
public class KafkaTransactionProducer<V extends TransactionCreateDto> {

  private final KafkaTemplate<String, V> template;

  public void send(V dto) {
    try {
      template.sendDefault(dto.getAccountId().toString(), dto).get();
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    } finally {
      template.flush();
    }
  }
}
