package ru.t1.java.demo.service.impl;

import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.dto.AccountMockDto;

@Service
public class AccountMockParseService extends MockDataParseServiceImpl<AccountMockDto> {

  public final String ACCOUNT_MOCK_FILE_PATH = "mock-data/ACCOUNT_DATA.json";

  public AccountMockParseService() {
    super(AccountMockDto[].class);
  }

  @Override
  public String getMockFilePath() {
    return ACCOUNT_MOCK_FILE_PATH;
  }
}
