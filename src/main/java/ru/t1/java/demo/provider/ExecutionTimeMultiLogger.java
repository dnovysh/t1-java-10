package ru.t1.java.demo.provider;

import java.util.Arrays;
import java.util.List;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class ExecutionTimeMultiLogger implements ExecutionTimeAspectLogProvider {

  private final List<ExecutionTimeAspectLogProvider> providers;

  public ExecutionTimeMultiLogger(ExecutionTimeAspectLogProvider... providers) {
    val thisClazz = this.getClass();
    this.providers = Arrays.stream(providers).filter(p -> p.getClass() != thisClazz).toList();
  }

  @Override
  public void logExecutionTime(JoinPoint joinPoint, long runningTimeMillis) {
    for (ExecutionTimeAspectLogProvider provider : providers) {
      provider.logExecutionTime(joinPoint, runningTimeMillis);
    }
  }
}
