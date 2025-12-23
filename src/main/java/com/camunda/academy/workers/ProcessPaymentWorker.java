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
public class ProcessPaymentWorker {

    private static final Logger logger = LoggerFactory.getLogger(ProcessPaymentWorker.class);
    
    private final TrackingOrderService trackingOrderService;

    @Autowired
    public ProcessPaymentWorker(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "processPayment")
    public void processPaymentHandler(final JobClient client, final ActivatedJob job) throws Exception {    
        logger.info("Handling job: {} Processing payment", job.getKey());
        trackingOrderService.processPayment(job);
        logger.info("Handling job: {} Payment processed successfully", job.getKey());
    }
}
