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
import ru.t1.java.demo.mapper.ClientMapper;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.model.entity.Client;
import ru.t1.java.demo.service.ClientService;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaClientConsumer {

  private final ClientService clientService;

  private final ClientMapper clientMapper;

  @KafkaListener(id = "t1-kafka-client-listener}",
      idIsGroup = false,
      topics = "${t1.kafka.topic.client_registration}",
      containerFactory = "clientKafkaListenerContainerFactory")
  public void listener(@Payload List<ClientDto> messageList,
      Acknowledgment ack,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      @Header(KafkaHeaders.RECEIVED_KEY) String key) {
    log.debug("Client consumer: Обработка новых сообщений");

    try {
      List<Client> clients = messageList.stream()
          .map(dto -> {
            dto.setFirstName(key + "@" + dto.getFirstName());
            return clientMapper.toEntity(dto);
          })
          .toList();
      clientService.registerClients(clients);
    } finally {
      ack.acknowledge();
    }

    log.debug("Client consumer: записи обработаны");
  }
}
