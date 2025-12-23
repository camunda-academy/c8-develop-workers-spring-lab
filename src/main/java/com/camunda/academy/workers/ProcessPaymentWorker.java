package com.camunda.academy.workers;

import com.camunda.academy.services.TrackingOrderService;

import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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
    public Map<String, Object> processPaymentHandler(final JobClient client, final ActivatedJob job, @Variable String orderId) throws Exception {
        logger.info("Order: {} Processing payment", orderId);
        final String paymentConfirmation = trackingOrderService.processPayment(job);
        logger.info("Order: {} Successful Transaction: {}", orderId, paymentConfirmation);
        logger.info("Order: {} Payment processed successfully", orderId);

        // Return only the variable this worker is responsible for
        return Map.of("paymentConfirmation", paymentConfirmation);
    }
}
