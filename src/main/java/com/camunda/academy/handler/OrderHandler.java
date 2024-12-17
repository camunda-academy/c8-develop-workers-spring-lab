package com.camunda.academy.handler;

import com.camunda.academy.services.TrackingOrderService;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderHandler.class);

    private final TrackingOrderService trackingOrderService;

    @Autowired
    public OrderHandler(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "trackOrderStatus")
    public void handleOrderStatusJob(final JobClient client, final ActivatedJob job) throws InterruptedException {
        logger.info("Handling job: {} Tracking status", job.getKey());
        trackingOrderService.trackOrderStatus(job);
        logger.info("Handling job: {} Order status tracked successfully", job.getKey());
    }
}