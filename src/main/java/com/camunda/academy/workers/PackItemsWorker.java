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
public class PackItemsWorker {

    private static final Logger logger = LoggerFactory.getLogger(PackItemsWorker.class);

    private final TrackingOrderService trackingOrderService;

    @Autowired
    public PackItemsWorker(TrackingOrderService trackingOrderService) {
        this.trackingOrderService = trackingOrderService;
    }

    @JobWorker(type = "packItems")
    public void packItemsHandler(final JobClient client, final ActivatedJob job) throws Exception {
        logger.info("Handling job: {} Packing items", job.getKey());
        trackingOrderService.packItems(job);
        logger.info("Handling job: {} Items packed successfully", job.getKey());
    }
}
