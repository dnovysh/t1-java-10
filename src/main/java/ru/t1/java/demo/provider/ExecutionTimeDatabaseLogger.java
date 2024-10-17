package ru.t1.java.demo.provider;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.entity.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

@Slf4j
@Service
public class ExecutionTimeDatabaseLogger implements ExecutionTimeAspectLogProvider {

  private final TimeLimitExceedLogRepository repository;

  private final long threshold;

  public ExecutionTimeDatabaseLogger(TimeLimitExceedLogRepository repository,
      @Value("${t1-aop.time-limit-logging.db.threshold-millis:1000}") long threshold) {
    this.repository = repository;
    this.threshold = threshold;
  }

  @Override
  public void logExecutionTime(JoinPoint joinPoint, long runningTimeMillis) {
    if (runningTimeMillis <= threshold) {
      return;
    }
    val signature = joinPoint.getSignature();
    try {
      val timeLimitExceedLog = TimeLimitExceedLog.builder()
          .methodSignature(signature.toShortString())
          .runningTimeMillis(runningTimeMillis)
          .build();
      repository.save(timeLimitExceedLog);
    } catch (Exception e) {
      log.error("Error writing execution time to DB for {}", signature.getName(), e);
    }
  }
}
