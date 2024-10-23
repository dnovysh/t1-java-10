package ru.t1.java.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionTimeKafkaLogDto {

  private Long runningTimeMillis;
  private String methodName;
  private String[] parameters;
  private Object[] args;
}
