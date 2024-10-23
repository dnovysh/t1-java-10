package ru.t1.java.demo.kafka;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.model.dto.AccountCreateDto;

@Slf4j
@RequiredArgsConstructor
public class KafkaAccountProducer<V extends AccountCreateDto> {

  private final KafkaTemplate<String, V> template;

  public void send(V dto) {
    try {
      template.sendDefault(UUID.randomUUID().toString(), dto).get();
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    } finally {
      template.flush();
    }
  }
}
