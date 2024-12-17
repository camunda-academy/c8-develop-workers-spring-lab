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
public class PackItemsHandler {

    private static final Logger logger = LoggerFactory.getLogger(PackItemsHandler.class);

    private final TrackingOrderService trackingOrderService;

    @Autowired
    public PackItemsHandler(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "packItems")
    public void packItemsHandler(final JobClient client, final ActivatedJob job) throws Exception {
        logger.info("Handling job: {} Packing items", job.getKey());
        trackingOrderService.packItems(job);
        logger.info("Handling job: {} Items packed successfully", job.getKey());
    }
}
