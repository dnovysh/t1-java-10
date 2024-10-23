package ru.t1.java.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.mapper.AccountMapper;
import ru.t1.java.demo.mapper.Mapper;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.model.dto.AccountMockDto;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.service.AccountService;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final ClientRepository clientRepository;
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final Mapper mapper;

  @Transactional
  @Override
  public AccountDto saveMock(AccountMockDto accountMockDto) {
    val account = accountMapper.toEntity(accountMockDto, clientRepository::getReferenceById);
    return mapper.toDto(accountRepository.saveAndFlush(account));
  }
}
