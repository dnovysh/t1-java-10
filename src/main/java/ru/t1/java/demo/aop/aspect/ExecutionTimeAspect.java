package ru.t1.java.demo.aop.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import ru.t1.java.demo.model.entity.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExecutionTimeAspect {

  private final TimeLimitExceedLogRepository repository;

  @Pointcut("within(ru.t1.java.demo..*)")
  public void methodsToBeProfiled() {
  }

  @Around("methodsToBeProfiled()")
  public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    StopWatch sw = new StopWatch(getClass().getSimpleName());
    val signature = proceedingJoinPoint.getSignature();
    try {
      sw.start(signature.toShortString());
      return proceedingJoinPoint.proceed();
    } finally {
      sw.stop();
      saveExecutionTime(signature, sw.getTotalTimeMillis());
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
