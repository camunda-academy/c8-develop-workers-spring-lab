package com.camunda.academy.workers;

import com.camunda.academy.services.TrackingOrderService;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderWorker {

    private static final Logger logger = LoggerFactory.getLogger(OrderWorker.class);

    private final TrackingOrderService trackingOrderService;

    @Autowired
    public OrderWorker(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "trackOrderStatus")
    public void handleOrderStatusJob(final JobClient client, final ActivatedJob job) throws InterruptedException {
        logger.info("Handling job: {} Tracking status", job.getKey());
        trackingOrderService.trackOrderStatus(job);
        logger.info("Handling job: {} Order status tracked successfully", job.getKey());
    }
}