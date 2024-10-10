package ru.t1.java.demo.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import ru.t1.java.demo.model.entity.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

  private final TimeLimitExceedLogRepository repository;

  private final long threshold;

  public ExecutionTimeAspect(TimeLimitExceedLogRepository repository,
      @Value("${t1-aop.time-limit-logging.db.threshold-millis:1000}") long threshold) {
    this.repository = repository;
    this.threshold = threshold;
  }

  @Pointcut("within(ru.t1.java.demo..*)")
  public void methodsToBeProfiled() {
  }

  @Around("methodsToBeProfiled()")
  public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    val signature = proceedingJoinPoint.getSignature();
    StopWatch sw = new StopWatch(getClass().getSimpleName());
    sw.start(signature.toShortString());
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      sw.stop();
      val totalMillis = sw.getTotalTimeMillis();
      if (totalMillis > threshold) {
        saveExecutionTime(signature, totalMillis);
      }
    }
  }

  private void saveExecutionTime(Signature signature, long runningTimeMillis) {
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
