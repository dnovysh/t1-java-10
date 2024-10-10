package ru.t1.java.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link ru.t1.java.demo.model.entity.Account}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountMockDto extends AccountCreateDto implements Serializable {

  private Long id;
}
