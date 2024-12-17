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
public class ProcessPaymentHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProcessPaymentHandler.class);
    
    private final TrackingOrderService trackingOrderService;

    @Autowired
    public ProcessPaymentHandler(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "processPayment")
    public void processPaymentHandler(final JobClient client, final ActivatedJob job) throws Exception {    
        logger.info("Handling job: {} Processing payment", job.getKey());
        trackingOrderService.processPayment(job);
        logger.info("Handling job: {} Payment processed successfully", job.getKey());
    }
}
