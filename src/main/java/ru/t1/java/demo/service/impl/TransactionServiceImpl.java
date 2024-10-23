package ru.t1.java.demo.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.mapper.Mapper;
import ru.t1.java.demo.mapper.TransactionMapper;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.model.dto.TransactionMockDto;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;
  private final Mapper mapper;

  @Transactional
  @Override
  public TransactionDto saveMock(TransactionMockDto transactionMockDto) {
    val transaction = transactionMapper.toEntity(transactionMockDto,
        accountRepository::getReferenceById);
    return mapper.toDto(transactionRepository.saveAndFlush(transaction));
  }

  public List<TransactionDto> saveMocks(List<TransactionMockDto> transactionMockDtos) {
    val transactions = transactionMockDtos.stream()
        .map((dto) -> transactionMapper.toEntity(dto, accountRepository::getReferenceById))
        .toList();
    val savedTransactions = transactionRepository.saveAllAndFlush(transactions);
    return savedTransactions.stream().map(mapper::toDto).toList();
  }
}
