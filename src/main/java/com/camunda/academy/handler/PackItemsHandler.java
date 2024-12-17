package com.camunda.academy.handler;

import com.camunda.academy.services.TrackingOrderService;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PackItemsHandler {

    private static final Logger logger = LoggerFactory.getLogger(PackItemsHandler.class);

    private final TrackingOrderService trackingOrderService;

    @Autowired
    public PackItemsHandler(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "packItems")
    public Map<String, Object> packItemsHandler(final JobClient client, final ActivatedJob job, @Variable String orderId) throws Exception {
        logger.info("Order: {} Packing items", orderId);
        final Boolean packedItems = trackingOrderService.packItems(job);
        logger.info("Order: {} Items packed successfully", orderId);
        return Map.of("packaged", packedItems);
    }
}
