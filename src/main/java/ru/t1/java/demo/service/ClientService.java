package ru.t1.java.demo.service;

import java.util.List;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.model.entity.Client;

public interface ClientService {

  List<Client> registerClients(List<Client> clients);

  Client registerClient(Client client);

  List<ClientDto> parseJson();

  void clearMiddleName(List<ClientDto> dtos);
}
