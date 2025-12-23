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
public class PackItemsWorker {

    private static final Logger logger = LoggerFactory.getLogger(PackItemsWorker.class);

    private final TrackingOrderService trackingOrderService;

    @Autowired
    public PackItemsWorker(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "packItems")
    public Map<String, Object> packItemsHandler(final JobClient client, final ActivatedJob job, @Variable String orderId) throws Exception {
        logger.info("Order: {} Packing items", orderId);
        final Boolean packedItems = trackingOrderService.packItems(job);
        logger.info("Order: {} Items packed successfully", orderId);

        // Return only the variable this worker is responsible for
        return Map.of("packaged", packedItems);
    }
}
