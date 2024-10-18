package ru.t1.java.demo.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

  @GetMapping(value = "/accounts/mock-save-async")
  public void mockSaveAsync() throws IOException, InterruptedException {

  }
}
