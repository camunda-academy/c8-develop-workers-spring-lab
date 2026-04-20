# Camunda 8 - Develop Workers (Spring Boot) - Lab

Lab project for the [Camunda 8 - Develop Workers (Spring Boot)](https://academy.camunda.com/c8-develop-workers-spring/) course on Camunda Academy.

This project demonstrates how to implement Camunda job workers with Spring Boot using the Camunda Spring Boot Starter. It focuses on worker behavior, variable handling, output mapping, and the relationship between BPMN service tasks and Java worker implementations.

Difficulty: Intermediate | Time: ~1h 30min | Platform: Camunda 8.9.0+

## What You Will Learn

- Understand how Camunda job workers execute business logic in a Spring Boot application
- Configure and implement workers for different job types
- Work with input variables using `@Variable`
- Return output variables from workers to the process instance
- Simulate business logic such as order tracking, item packaging, and payment processing
- Start process instances programmatically with randomized input data

## Overview

The BPMN process `orderProcess` is defined in `src/main/resources/order.bpmn` and contains three service tasks handled by Spring Boot workers:

| BPMN Task | Job Type | Spring Worker | Behavior |
| --- | --- | --- | --- |
| Track order | `trackOrderStatus` | `OrderWorker` | Simulates tracking with a 10-second delay |
| Pack items | `packItems` | `PackItemsWorker` | Returns `packaged=true` |
| Process payment | `processPayment` | `ProcessPaymentWorker` | Returns a generated payment confirmation |

On startup, the application:

1. Connects to a Camunda 8 SaaS cluster using the configured client credentials.
2. Generates randomized order variables with `FakeRandomizer`.
3. Starts one new process instance of `orderProcess`.
4. Activates Spring-based job workers that poll for and complete the service tasks.

## Prerequisites

### Knowledge

- Awareness of Camunda 8 and job workers
- Competence with BPMN
- Basic Java and Spring Boot knowledge

Recommended preparatory courses:

- [Camunda - Technical Overview](https://academy.camunda.com/c8-technical-overview)
- [Camunda - Develop your first job worker in Java](https://academy.camunda.com/c8-develop-first-worker-java/)
- [Camunda - Error Handling](https://academy.camunda.com/c8-error-handling)

### Tools And Access

- Java 21+
- Maven 3.x
- An IDE such as VS Code, IntelliJ IDEA, or Eclipse
- A Camunda 8 SaaS account
- A running Camunda 8 cluster
- Client credentials for that cluster

## Configuration

This project is configured for Camunda SaaS using environment variables or setting the client credentials in `src/main/resources/application.yaml`:

```yaml
camunda:
	client:
		mode: saas
		auth:
			client-id: xxx
			client-secret: xxx
		cloud:
			cluster-id: xxx
			region: xxx
```

Replace the placeholder values with your actual Camunda 8 SaaS credentials:

- `client-id`
- `client-secret`
- `cluster-id`
- `region`

These values are available in the Camunda Console when you create API credentials for your cluster.

Before running the application, deploy `src/main/resources/order.bpmn` to your Camunda cluster using Camunda Modeler or Web Modeler.

## Build

Build the project with:

```bash
./mvnw clean package
```

On Windows:

```powershell
.\mvnw.cmd clean package
```

## Run

Run the Spring Boot application with:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

When the application starts, it will:

1. Bootstrap the Spring Boot context and Camunda client.
2. Create one `orderProcess` instance with randomized order data.
3. Activate workers for `trackOrderStatus`, `packItems`, and `processPayment`.
4. Complete the process once both parallel tasks finish.

## Example Process Variables

The `FakeRandomizer` class generates example variables such as:

- `orderId`
- `productName`
- `price`
- `promotionCode`
- `material`
- `department`
- `packaged`
- `paymentConfirmation`

Worker output variables are written back to the process instance:

- `PackItemsWorker` returns `packaged`
- `ProcessPaymentWorker` returns `paymentConfirmation`

## Project Structure

```text
src/main/java/com/camunda/academy/
├── OrderApplication.java
├── FakeRandomizer.java
├── services/
│   └── TrackingOrderService.java
└── workers/
	├── OrderWorker.java
	├── PackItemsWorker.java
	└── ProcessPaymentWorker.java

src/main/resources/
├── application.yaml
└── order.bpmn
```

## Key Components

### `OrderApplication`

- Spring Boot entry point
- Implements `CommandLineRunner`
- Starts one process instance of `orderProcess` on application startup

### `OrderWorker`

- Handles jobs of type `trackOrderStatus`
- Reads `orderId`
- Delegates to `TrackingOrderService.trackOrderStatus`

### `PackItemsWorker`

- Handles jobs of type `packItems`
- Reads `orderId`
- Returns `Map.of("packaged", true)`

### `ProcessPaymentWorker`

- Handles jobs of type `processPayment`
- Reads `orderId`
- Returns a generated `paymentConfirmation`

### `TrackingOrderService`

- Simulates business logic for all three worker actions
- Introduces a 10-second delay for order tracking
- Returns simple values for packaging and payment confirmation

## Testing

The project includes Spring Boot test support through Maven and Camunda test dependencies.

Note that a full context-loading test may require a reachable and correctly configured Camunda environment. If the configured cluster is unavailable, tests can fail during application startup even when the project compiles and packages successfully.

## Dependencies

Main dependencies used by this lab:

| Dependency | Version | Purpose |
| --- | --- | --- |
| `org.springframework.boot:spring-boot-starter-parent` | `4.0.5` | Spring Boot parent and dependency management |
| `io.camunda:camunda-spring-boot-starter` | `8.9.0` | Camunda Spring Boot integration |
| `io.camunda:camunda-process-test-spring` | `8.9.0` | Camunda process testing support |
| `com.github.javafaker:javafaker` | `1.0.2` | Random test/order data generation |
| `org.junit.jupiter:junit-jupiter` | `5.14.1` | JUnit 5 testing |

## Troubleshooting

- Verify that `order.bpmn` is deployed to the target cluster before starting the app.
- Verify that the values in `application.yaml` match your Camunda SaaS cluster and API credentials.
- If startup fails with connection or `503 Service Temporarily Unavailable`, check cluster availability and credentials.
- If tests fail while the application packages successfully, the issue is likely environment connectivity rather than Java compilation.

## Related Links

- [Course: Camunda 8 - Develop Workers (Spring Boot)](https://academy.camunda.com/c8-develop-workers-spring/)
- [Reference lab: c8-develop-workers-java-lab](https://github.com/camunda-academy/c8-develop-workers-java-lab)

## License

This repository includes an Apache 2.0 license. See `LICENSE` for details.

