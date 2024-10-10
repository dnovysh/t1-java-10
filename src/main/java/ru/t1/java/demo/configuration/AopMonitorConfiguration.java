package ru.t1.java.demo.configuration;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.t1.java.demo.aop.interceptor.CustomPerformanceMonitorInterceptor;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AopMonitorConfiguration {

  private final long threshold;

  public AopMonitorConfiguration(
      @Value("${t1-aop.time-limit-logging.logger.threshold-millis:200}") long threshold) {
    this.threshold = threshold;
  }

  @Pointcut("execution(* ru.t1.java.demo.controller.ClientController.doSomething())")
  public void customMonitor() {
  }

  /**
   * Если useDynamicLogger установлен в true, для объектов попадающих в pointcut уровень логирования
   * должен быть установлен в TRACE, в противном случае лог не выводится, данное ограничение
   * установлено в AbstractMonitoringInterceptor
   */
  @Bean
  public CustomPerformanceMonitorInterceptor customPerformanceMonitorInterceptor() {
    return new CustomPerformanceMonitorInterceptor(true, threshold);
  }

  @Bean
  public Advisor customPerformanceMonitorAdvisor() {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("ru.t1.java.demo.configuration.AopMonitorConfiguration.customMonitor()");
    return new DefaultPointcutAdvisor(pointcut, customPerformanceMonitorInterceptor());
  }
}
