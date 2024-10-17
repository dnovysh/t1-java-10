package ru.t1.java.demo.provider;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExecutionTimeKafkaLogger implements ExecutionTimeAspectLogProvider {

  @Override
  public void logExecutionTime(JoinPoint joinPoint, long runningTimeMillis) {
    // ToDo реализовать логирование в Кафку
  }
}
