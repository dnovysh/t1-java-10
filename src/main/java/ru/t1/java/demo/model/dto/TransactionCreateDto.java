package ru.t1.java.demo.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.t1.java.demo.model.entity.Transaction;

/**
 * DTO for {@link Transaction}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TransactionCreateDto implements Serializable {

  private Long accountId;
  private BigDecimal amount;
}
