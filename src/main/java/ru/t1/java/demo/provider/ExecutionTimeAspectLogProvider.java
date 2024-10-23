package ru.t1.java.demo.provider;

import org.aspectj.lang.JoinPoint;

public interface ExecutionTimeAspectLogProvider {

  void logExecutionTime(JoinPoint joinPoint, long runningTimeMillis);
}
