package ru.t1.java.demo.service;

import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.model.dto.AccountMockDto;

public interface AccountService {

  AccountDto saveMock(AccountMockDto accountMockDto);
}
