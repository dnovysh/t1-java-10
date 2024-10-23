package ru.t1.java.demo.service.impl;

import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.dto.TransactionMockDto;

@Service
public class TransactionMockParseService extends MockDataParseServiceImpl<TransactionMockDto> {

  public final String TRANSACTION_MOCK_FILE_PATH = "mock-data/TRANSACTION_DATA.json";

  public TransactionMockParseService() {
    super(TransactionMockDto[].class);
  }

  @Override
  public String getMockFilePath() {
    return TRANSACTION_MOCK_FILE_PATH;
  }
}
