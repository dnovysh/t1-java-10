package ru.t1.java.demo.mapper;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.dto.TransactionMockDto;
import ru.t1.java.demo.model.entity.Account;
import ru.t1.java.demo.model.entity.Transaction;

@Component
public class TransactionMapper {

  public Transaction toEntity(TransactionMockDto dto, Function<Long, Account> referenceById) {
    return Transaction.builder()
        .id(dto.getId())
        .account(referenceById.apply(dto.getAccountId()))
        .amount(dto.getAmount())
        .version(0L)
        .build();
  }
}
