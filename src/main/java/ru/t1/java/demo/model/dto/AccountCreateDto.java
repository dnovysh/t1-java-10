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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountCreateDto implements Serializable {

  private Long clientId;
  private AccountType accountType;
  private BigDecimal balance;
}
