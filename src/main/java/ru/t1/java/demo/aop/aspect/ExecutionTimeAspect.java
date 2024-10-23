package ru.t1.java.demo.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import ru.t1.java.demo.provider.ExecutionTimeAspectLogProvider;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

  private final ExecutionTimeAspectLogProvider logProvider;

  public ExecutionTimeAspect(ExecutionTimeAspectLogProvider logProvider) {
    this.logProvider = logProvider;
  }

  @Pointcut("@annotation(ru.t1.java.demo.aop.annotation.Track)")
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
      logProvider.logExecutionTime(proceedingJoinPoint, totalMillis);
    }
  }
}
