package ru.t1.java.demo.aop.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;

public class CustomPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {

  private final long threshold;

  public CustomPerformanceMonitorInterceptor(long threshold) {
    this.threshold = threshold;
  }

  public CustomPerformanceMonitorInterceptor(boolean useDynamicLogger, long threshold) {
    super.setUseDynamicLogger(useDynamicLogger);
    this.threshold = threshold;
  }

  @Override
  protected Object invokeUnderTrace(@NonNull MethodInvocation invocation, @NonNull Log logger)
      throws Throwable {
    String name = createInvocationTraceName(invocation);
    StopWatch stopWatch = new StopWatch(name);
    stopWatch.start(name);
    try {
      return invocation.proceed();
    } finally {
      stopWatch.stop();
      if (stopWatch.getTotalTimeMillis() > threshold) {
        writeToLog(logger, stopWatch.shortSummary());
      }
    }
  }
}
