package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.entity.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataSourceErrorAspect {

  private final DataSourceErrorLogRepository repository;

  @Pointcut("within(ru.t1.java.demo..*)")
  public void loggingMethods() {
  }

  @AfterThrowing(pointcut = "loggingMethods()", throwing = "ex")
  public void logException(JoinPoint joinPoint, Throwable ex) {
    try {
      val dataSourceErrorLog = DataSourceErrorLog.builder()
          .methodSignature(joinPoint.getSignature().getName())
          .message(ex.getMessage())
          .stackTrace(ExceptionUtils.getStackTrace(ex))
          .build();
      repository.save(dataSourceErrorLog);
    } catch (Exception e) {
      log.error("Error writing error log to DB for {} with message {}",
          joinPoint.getSignature().getName(), ex.getMessage(), e);
    }
  }
}
