package com.camunda.academy;

import io.camunda.zeebe.client.ZeebeClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class OrderApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(OrderApplication.class);

    // Process instance creation
    private static final String PROCESS_ID = "orderProcess";
    private static final int NUM_INSTANCES = 4; // Set the total number of new process instances

    @Autowired
    private ZeebeClient zeebeClient;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        startProcessInstances(zeebeClient, NUM_INSTANCES);
    }

    public void startProcessInstances(ZeebeClient zeebeClient, int numInstances) {
        logger.info("Starting: " + numInstances + " process instances for process: " + PROCESS_ID);
        for (int i = 0; i < numInstances; i++) {
            FakeRandomizer fakeRandomizer = new FakeRandomizer();
            Map<String, Object> fakeRequest = fakeRandomizer.getRandom();
            logger.info("Generating Order({})",fakeRequest.get("orderId"));
            var event = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .variables(fakeRequest)
                .send()
                .join();
            logger.info("Process instance: {} started", event.getProcessInstanceKey());
        }
        logger.info("Ending: " + numInstances + " instances created for process: " + PROCESS_ID);
    }
}
