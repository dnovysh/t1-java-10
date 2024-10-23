package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.aop.annotation.Track;
import ru.t1.java.demo.kafka.KafkaAccountProducer;
import ru.t1.java.demo.model.dto.AccountMockDto;
import ru.t1.java.demo.service.impl.MockDataParseServiceImpl;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

  private final KafkaAccountProducer<AccountMockDto> producer;

  private final MockDataParseServiceImpl<AccountMockDto> parseService;

  @Track
  @PostMapping(value = "/accounts/mock/produce-data")
  public void produceMockData() {
    val mockDtos = parseService.parseJson();
    mockDtos.forEach(producer::send);
  }
}
