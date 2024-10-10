package ru.t1.java.demo.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.t1.java.demo.model.enums.AccountType;

/**
 * DTO for {@link ru.t1.java.demo.model.entity.Account}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDto implements Serializable {

  private Long id;
  private ClientDto client;
  private AccountType accountType;
  private BigDecimal balance;
  private Long version;
}
