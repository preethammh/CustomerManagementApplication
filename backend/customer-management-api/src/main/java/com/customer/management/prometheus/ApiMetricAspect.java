package com.customer.management.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ApiMetricAspect {

    private final MeterRegistry meterRegistry;

    public ApiMetricAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // Intercepts any execution containing the @TrackCount annotation
    @Before("@annotation(com.customer.management.prometheus.TrackCount)")
    public void incrementApiCounter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        TrackCount trackCount = method.getAnnotation(TrackCount.class);

        // Fallback to method name if no explicit label value is provided
        String metricLabel = trackCount.value().isEmpty() ? method.getName() : trackCount.value();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Register and increment a counter metric named 'custom_api_calls_total'
        Counter.builder("customer_management_api_calls_total")
                .description("Tracks total count of custom annotated API executions")
                .tag("class", className)
                .tag("method", metricLabel)
                .register(meterRegistry)
                .increment();
    }
}
