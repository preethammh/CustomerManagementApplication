package com.customer.management.ratelimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Integer bucketCapacity;
    private final Integer refillTokens;
    private final Integer refillDuration;

    public RateLimitingFilter(@Value("${bucket.capacity}") Integer bucketCapacity,
                              @Value("${bucket.refill.tokens}") Integer refillTokens,
                              @Value("${bucket.refill.duration.minutes}") Integer refillDuration) {
        this.bucketCapacity = bucketCapacity;
        this.refillTokens = refillTokens;
        this.refillDuration = refillDuration;
    }
    // Map to store buckets per IP address
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();


    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(limit->limit
                        .capacity(bucketCapacity)
                        .refillIntervally(refillTokens, Duration.ofMinutes(refillDuration)))
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        Bucket bucket = cache.computeIfAbsent(ip, k -> createNewBucket());

        // Verbose consumption provides remaining tokens and refill time
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            response.addHeader("X-RateLimit-Remaining", String.valueOf(probe.getRemainingTokens()));
            filterChain.doFilter(request, response);
        } else {
            // Calculate seconds until the bucket is refilled
            long waitForRefillSeconds = TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill());

            // Populate standard 429 response data
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.addHeader("Retry-After", String.valueOf(waitForRefillSeconds));
            response.addHeader("X-RateLimit-Retry-After-Seconds", String.valueOf(waitForRefillSeconds));

            // Construct standard error payload
            String jsonPayload = String.format(
                    "{\"status\": 429, \"error\": \"Too Many Requests\", \"message\": \"Rate limit exceeded.\", \"retryAfterSeconds\": %d}",
                    waitForRefillSeconds
            );

            response.getWriter().write(jsonPayload);
        }
    }

}
