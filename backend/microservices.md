# Microservices Architecture Q&A

## 1. What are the design principles of Microservices?

- Single Responsibility
- Autonomy
- Decentralized Data Management
- Failure Isolation
- Independently Deployable
- Scalability

## 2. What communication protocols are commonly used between microservices?

- REST (HTTP/HTTPS)
- gRPC
- Message Queues (e.g., RabbitMQ, Apache Kafka)
- WebSockets

## 3. Explain service discovery in microservices architecture.

Service discovery is a mechanism that allows services to locate and communicate with each other dynamically. It typically involves a registry where services register themselves and can be looked up by other services.

## 4. How can microservices be deployed?

- Containers (e.g., Docker)
- Orchestration platforms (e.g., Kubernetes)
- Serverless platforms
- Virtual machines

## 5. What will be your approach to migrate a monolithic application to microservices?

- Analyze the monolith and identify bounded contexts
- Start with extracting non-core functionalities
- Implement new features as microservices
- Gradually break down the monolith, starting with the least coupled components
- Use strangler pattern to incrementally replace monolith functionalities

## 6. How to handle a situation where Microservice Service A calls Service B and Service B calls Service C, but Service B is down?

Implement circuit breakers and fallback mechanisms. Service A should detect that B is down and either use cached data, provide degraded functionality, or return a graceful error response.

## 7. At which layer do we implement security in microservices?

Security should be implemented at multiple layers:
- Network level
- API Gateway
- Service-to-service communication
- Data storage

## 8. How to use Feign Client?

Feign is a declarative HTTP client used in Java. To use it:
1. Add Feign dependency
2. Enable Feign clients in your application
3. Create an interface and annotate it with @FeignClient
4. Define methods in the interface that map to API endpoints

## 9. Which Microservice design pattern would you use for read-heavy and write-heavy applications?

- Read-heavy: CQRS (Command Query Responsibility Segregation) with Read Replicas
- Write-heavy: Event Sourcing with CQRS

## 10. How to call other microservices asynchronously?

- Use message queues (e.g., RabbitMQ, Apache Kafka)
- Implement event-driven architecture
- Use reactive programming models (e.g., Spring WebFlux)
- Employ async HTTP clients
