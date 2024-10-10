package ru.t1.java.demo.model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link ru.t1.java.demo.model.entity.Account}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountUpdateDto extends AccountCreateDto implements Serializable {

  private Long id;
  private Long version;
}
