package ru.t1.java.demo.mapper;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.dto.AccountMockDto;
import ru.t1.java.demo.model.entity.Account;
import ru.t1.java.demo.model.entity.Client;

@Component
public class AccountMapper {

  public Account toEntity(AccountMockDto dto, Function<Long, Client> referenceById) {
    return Account.builder()
        .id(dto.getId())
        .client(referenceById.apply(dto.getClientId()))
        .accountType(dto.getAccountType())
        .balance(dto.getBalance())
        .build();
  }
}
