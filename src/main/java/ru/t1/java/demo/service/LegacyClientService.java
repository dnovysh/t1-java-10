package ru.t1.java.demo.service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.exception.ClientException;
import ru.t1.java.demo.model.entity.Client;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.util.ClientMapper;

@Service
@Slf4j
public class LegacyClientService {

  private final ClientRepository repository;
  private final Map<Long, Client> cache;

  public LegacyClientService(ClientRepository repository) {
    this.repository = repository;
    this.cache = new HashMap<>();
  }

  @PostConstruct
  void init() {
    val client = getClient(1L);
    if (client != null) {
      log.debug("Init cache - client with id {} added to cache", client.getId());
    } else {
      log.debug("Init cache - no clients added to cache");
    }
  }

  public ClientDto getClient(Long id) {
    log.debug("Call method getClient with id {}", id);
    ClientDto clientDto = null;

    if (cache.containsKey(id)) {
      return ClientMapper.toDto(cache.get(id));
    }

    try {
      Client entity = repository.findById(id)
          .orElseThrow(() -> new ClientException("Not Found Client with id %d".formatted(id)));
      clientDto = ClientMapper.toDto(entity);
      cache.put(id, entity);
    } catch (Exception e) {
      log.error("Error: {}", e.getMessage());
//            throw new ClientException();
    }

//        log.debug("Client info: {}", clientDto.toString());
    return clientDto;
  }

}
