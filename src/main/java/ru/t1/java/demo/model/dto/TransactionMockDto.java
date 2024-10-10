package ru.t1.java.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMockDto extends TransactionCreateDto implements Serializable {

  private Long Id;
}
