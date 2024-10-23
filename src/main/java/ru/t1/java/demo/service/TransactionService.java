package ru.t1.java.demo.service;

import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.model.dto.TransactionMockDto;

public interface TransactionService {

  TransactionDto saveMock(TransactionMockDto transactionMockDto);
}
