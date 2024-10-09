package ru.t1.java.demo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
  DEBIT("Дебетовый счет"),
  CREDIT("Кредитный счет");

  private final String value;
}
