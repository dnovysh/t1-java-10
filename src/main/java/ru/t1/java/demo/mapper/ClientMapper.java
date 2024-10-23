package ru.t1.java.demo.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.model.entity.Client;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

  Client toEntity(ClientDto clientDto);

  ClientDto toDto(Client client);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Client partialUpdate(ClientDto clientDto, @MappingTarget Client client);
}