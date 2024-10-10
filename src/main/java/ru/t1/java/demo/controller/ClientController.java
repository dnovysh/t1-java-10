package ru.t1.java.demo.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.exception.ClientException;
import ru.t1.java.demo.service.ClientService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClientController {

  private final ClientService clientService;

//  @HandlingResult
//  @LogException
//  @Track
  @GetMapping(value = "/client")
  public void doSomething() throws IOException, InterruptedException {
//        try {
//            clientService.parseJson();
    Thread.sleep(3000L);
    throw new ClientException();
//        } catch (Exception e) {
//            log.info("Catching exception from ClientController");
//            throw new ClientException();
//        }
  }
}
