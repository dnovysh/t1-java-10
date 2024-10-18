package ru.t1.java.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.dto.AccountMockDto;
import ru.t1.java.demo.model.entity.Account;
import ru.t1.java.demo.service.AccountService;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  @Override
  public List<AccountMockDto> parseJson() throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    InputStream resource = new ClassPathResource("mock-data/ACCOUNT_DATA.json")
        .getInputStream();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
      AccountMockDto[] accounts = mapper.readValue(reader, AccountMockDto[].class);
      return Arrays.asList(accounts);
    }
  }

  @Override
  public List<Account> saveMock(Iterable<AccountMockDto> accountMockDtos) {
    return List.of();
  }
}
