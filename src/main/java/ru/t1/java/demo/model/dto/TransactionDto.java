package ru.t1.java.demo.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.t1.java.demo.model.entity.Transaction;

/**
 * DTO for {@link Transaction}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDto implements Serializable {

  private Long id;
  private AccountDto account;
  private BigDecimal amount;
  private Long version;
}