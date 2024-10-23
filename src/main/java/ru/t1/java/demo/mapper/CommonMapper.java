package ru.t1.java.demo.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.model.entity.Account;
import ru.t1.java.demo.model.entity.Client;
import ru.t1.java.demo.model.entity.Transaction;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = SPRING)
public interface CommonMapper {

  AccountDto accountToAccountDto(Account account);

  TransactionDto transactionToTransactionDto(Transaction transaction);

  Client clientDtoToClient(ClientDto clientDto);

  ClientDto clientToClientDto(Client client);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Client partialUpdate(ClientDto clientDto, @MappingTarget Client client);
}
