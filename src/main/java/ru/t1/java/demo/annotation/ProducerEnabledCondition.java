package ru.t1.java.demo.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(value = "t1.kafka.producer.enable",
    havingValue = "true",
    matchIfMissing = true)
public @interface ProducerEnabledCondition {

}
