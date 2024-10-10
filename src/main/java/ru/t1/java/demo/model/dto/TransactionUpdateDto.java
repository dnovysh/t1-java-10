package ru.t1.java.demo.model.dto;

import java.io.Serializable;
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
public class TransactionUpdateDto extends TransactionCreateDto implements Serializable {

  private Long Id;
  private Long version;
}
