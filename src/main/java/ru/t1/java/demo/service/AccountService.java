package ru.t1.java.demo.service;

import java.io.IOException;
import java.util.List;
import ru.t1.java.demo.model.dto.AccountMockDto;
import ru.t1.java.demo.model.entity.Account;

public interface AccountService {

  List<AccountMockDto> parseJson() throws IOException;

  List<Account> saveMock(Iterable<AccountMockDto> accountMockDtos);
}
