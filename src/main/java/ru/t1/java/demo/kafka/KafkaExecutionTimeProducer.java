package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.model.dto.ExecutionTimeKafkaLogDto;

@Slf4j
@RequiredArgsConstructor
public class KafkaExecutionTimeProducer<V extends ExecutionTimeKafkaLogDto> {

  private final KafkaTemplate<String, V> template;

  public void send(V dto) {
    try {
      template.sendDefault(dto.getMethodName(), dto).get();
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    } finally {
      template.flush();
    }
  }
}
