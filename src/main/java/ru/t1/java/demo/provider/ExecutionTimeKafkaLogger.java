package ru.t1.java.demo.provider;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.kafka.KafkaExecutionTimeProducer;
import ru.t1.java.demo.model.dto.ExecutionTimeKafkaLogDto;

@Slf4j
@Service
public class ExecutionTimeKafkaLogger implements ExecutionTimeAspectLogProvider {

  private final KafkaExecutionTimeProducer<ExecutionTimeKafkaLogDto> producer;

  private final long threshold;

  public ExecutionTimeKafkaLogger(KafkaExecutionTimeProducer<ExecutionTimeKafkaLogDto> producer,
      @Value("${t1.aop.time-limit-logging.kafka.threshold-millis:200}") long threshold) {
    this.producer = producer;
    this.threshold = threshold;
  }

  @Override
  public void logExecutionTime(JoinPoint joinPoint, long runningTimeMillis) {
    if (runningTimeMillis <= threshold) {
      return;
    }
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    var dto = ExecutionTimeKafkaLogDto.builder()
        .runningTimeMillis(runningTimeMillis)
        .methodName(signature.getMethod().getName())
        .parameters(signature.getParameterNames())
        .args(joinPoint.getArgs())
        .build();
    producer.send(dto);
  }
}
