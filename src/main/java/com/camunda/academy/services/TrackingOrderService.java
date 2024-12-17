package com.camunda.academy.services;

import io.camunda.zeebe.client.api.response.ActivatedJob;

import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class TrackingOrderService {
    private static final int TRACKING_TIME = 10; // Service duration in seconds

    public void trackOrderStatus(ActivatedJob job) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(TRACKING_TIME).toMillis());
    }

    public Boolean packItems(ActivatedJob job) {
        return true;
    }

    public String processPayment(ActivatedJob job) {
        return String.valueOf(System.currentTimeMillis());
    }
}