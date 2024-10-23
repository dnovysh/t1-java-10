package ru.t1.java.demo.kafka;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.model.dto.ClientDto;

@Slf4j
@RequiredArgsConstructor
public class KafkaClientProducer<ID, V extends ClientDto> {

  private final KafkaTemplate<String, ID> clientIdTemplate;
  private final KafkaTemplate<String, V> clientTemplate;

  public void send(ID clientId) {
    try {
      clientIdTemplate.sendDefault(UUID.randomUUID().toString(), clientId).get();
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    } finally {
      clientIdTemplate.flush();
    }
  }

  public void sendTo(String topic, V dto) {
    try {
      clientTemplate.send(topic, dto).get();
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    } finally {
      clientTemplate.flush();
    }
  }
}
