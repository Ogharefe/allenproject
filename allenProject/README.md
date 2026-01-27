# AllenProject Microservices

AllenProject is a JVM-based microservices system built with Spring Boot and a hexagonal architecture. Services communicate via Kafka (Spring Cloud Stream) and persist to PostgreSQL with Liquibase-managed migrations. Shared event DTOs and enums live in a dedicated module.

## Modules
- `event-contracts` — shared event models and enums ([pom.xml](file:///c:/clevaProject/project-allen/allenProject/event-contracts/pom.xml))
- `product-service` — products, stock, warehouses, event publishing/consuming ([application-local.yml](file:///c:/clevaProject/project-allen/allenProject/product-service/src/main/resources/application-local.yml))
- `purchase-service` — suppliers and purchase orders, event publishing/consuming ([application-local.yml](file:///c:/clevaProject/project-allen/allenProject/purchase-service/src/main/resources/application-local.yml))
- `sales-service` — sales lifecycle and product snapshots, consumes product events ([application-local.yml](file:///c:/clevaProject/project-allen/allenProject/sales-service/src/main/resources/application-local.yml))
- `gateway-service` — API gateway using Spring Cloud Gateway ([pom.xml](file:///c:/clevaProject/project-allen/allenProject/gateway-service/pom.xml))

Root aggregator POM declares the modules ([pom.xml](file:///c:/clevaProject/project-allen/allenProject/pom.xml)).

## Tech Stack
- Java 21, Spring Boot
- Spring Cloud Stream (Kafka binder)
- Spring Cloud Gateway
- PostgreSQL, Liquibase
- MapStruct for DTO/entity mapping
- Maven for build and packaging

## Service Ports
- product-service: local `3005`, docker `3001` ([product-service application-local.yml](file:///c:/clevaProject/project-allen/allenProject/product-service/src/main/resources/application-local.yml#L57-L58), [application-docker.yml](file:///c:/clevaProject/project-allen/allenProject/product-service/src/main/resources/application-docker.yml#L51-L52))
- purchase-service: local `3003`, docker `3002` ([purchase-service application-local.yml](file:///c:/clevaProject/project-allen/allenProject/purchase-service/src/main/resources/application-local.yml#L52-L54), [application-docker.yml](file:///c:/clevaProject/project-allen/allenProject/purchase-service/src/main/resources/application-docker.yml#L52-L54))
- sales-service: local/docker `8083` ([sales-service application-local.yml](file:///c:/clevaProject/project-allen/allenProject/sales-service/src/main/resources/application-local.yml#L1-L3))
- gateway-service: local/docker `8080` ([gateway application-local.yml](file:///c:/clevaProject/project-allen/allenProject/gateway-service/src/main/resources/application-local.yml), [application-docker.yml](file:///c:/clevaProject/project-allen/allenProject/gateway-service/src/main/resources/application-docker.yml))

## Kafka Topics (examples)
- product-creation-events
- product-update-events
- purchase-order-events
- stock-events
- stock-update-events (used by purchase-service)

Topic bindings are configured per service in their `application-*.yml` files.

## Gateway Routing
- Local profile routes (`8080` → localhost services):
  - `/api/products/**`, `/api/stocks/**`, `/api/warehouses/**` → product-service `http://localhost:3005`
  - `/api/purchaseorders/**`, `/api/suppliers/**` → purchase-service `http://localhost:3003`
  - `/api/v1/sales/**` → sales-service `http://localhost:8083`
- Docker profile routes (`8080` → container services):
  - `/api/products/**`, `/api/stocks/**`, `/api/warehouses/**` → product-service `http://product-service:3001`
  - `/api/purchaseorders/**`, `/api/suppliers/**` → purchase-service `http://purchase-service:3002`
  - `/api/v1/sales/**` → sales-service `http://sales-service:8083`
- Config files: [gateway application-local.yml](file:///c:/clevaProject/project-allen/allenProject/gateway-service/src/main/resources/application-local.yml), [gateway application-docker.yml](file:///c:/clevaProject/project-allen/allenProject/gateway-service/src/main/resources/application-docker.yml)

## REST APIs (high level)
- product-service
  - `GET /api/products`
  - `GET /api/products/{id}`
  - `POST /api/products`
  - `PUT /api/products/{id}`
  - `DELETE /api/products/{id}` ([ProductController](file:///c:/clevaProject/project-allen/allenProject/product-service/src/main/java/com/allen/product/infrastructure/web/rest/ProductController.java))
  - `GET /api/stocks`, `POST /api/stocks`, `POST /api/stocks/{stockId}/reserve` ([StockController](file:///c:/clevaProject/project-allen/allenProject/product-service/src/main/java/com/allen/product/infrastructure/web/rest/StockController.java))
  - `GET /api/warehouses`, `POST /api/warehouses`, `DELETE /api/warehouses/{id}` ([WarehouseController](file:///c:/clevaProject/project-allen/allenProject/product-service/src/main/java/com/allen/product/infrastructure/web/rest/WarehouseController.java))
- purchase-service
  - `POST /api/purchaseorders` ([PurchaseOrderController](file:///c:/clevaProject/project-allen/allenProject/purchase-service/src/main/java/com/allen/purchase/infrastructure/web/rest/PurchaseOrderController.java))
  - `GET /api/suppliers`, `DELETE /api/suppliers/{id}` ([ItemSupplierController](file:///c:/clevaProject/project-allen/allenProject/purchase-service/src/main/java/com/allen/purchase/infrastructure/web/rest/ItemSupplierController.java))
- sales-service
  - `POST /api/v1/sales`
  - `GET /api/v1/sales/{saleId}`
  - `DELETE /api/v1/sales/{saleId}`
  - `POST /api/v1/sales/{saleId}/items`
  - `DELETE /api/v1/sales/{saleId}/items/{productId}`
  - `PATCH /api/v1/sales/{saleId}/items/{productId}`
  - `POST /api/v1/sales/{saleId}/complete`
  - `POST /api/v1/sales/{saleId}/cancel` ([SaleController](file:///c:/clevaProject/project-allen/allenProject/sales-service/src/main/java/com/allen/sales/infrastructure/web/rest/SaleController.java))

## Run with Docker
1. Install Docker and Docker Compose
2. From `./docker`, start infra and services:
   - `docker-compose up -d`
3. Services:
   - gateway-service on `http://localhost:8080`
   - product-service on `http://localhost:3001`
   - purchase-service on `http://localhost:3002`
   - sales-service on `http://localhost:8083`
4. Tooling:
   - AKHQ (Kafka UI): `http://localhost:8088`
   - pgAdmin: `http://localhost:8081`

Compose file defines Kafka, Zookeeper, Postgres databases, and builds service images ([docker-compose.yml](file:///c:/clevaProject/project-allen/allenProject/docker/docker-compose.yml)).

## Run Locally (without Docker)
Prerequisites: Kafka broker reachable at `localhost:29092`, PostgreSQL instances or use Docker DB containers.

- Build all modules: from repo root, `mvn clean package`
- Run a service:
  - gateway-service: `mvn -pl gateway-service spring-boot:run`
  - product-service: `mvn -pl product-service spring-boot:run` (profile `local` is default; see [application.yml](file:///c:/clevaProject/project-allen/allenProject/product-service/src/main/resources/application.yml))
  - purchase-service: `mvn -pl purchase-service spring-boot:run`
  - sales-service: `mvn -pl sales-service spring-boot:run`

Environment can be adjusted via `SPRING_PROFILES_ACTIVE` (`local` or `docker`) and the `POSTGRES_*`/`KAFKA_*` variables defined in each service’s `application-*.yml`.

## Notes
- `warehouse-service` and `staff-service` directories exist for future expansion; they are not part of the current aggregator build.
- Database schemas are managed via Liquibase changelogs in each service.
- Event contracts are centralized in `event-contracts` to ensure compatibility across services.

Summary (what you should do)
In createSale, publish reservation commands (updateType=RESERVATION) and mark the sale as pending.
Let product-service choose warehouseId (don’t compute it in sales-service).
Have product-service emit explicit reservation success/failure events correlated by saleId.
On success: proceed to payment, then publish SALE command (or “commit reservation”).
On failure: mark sale as rejected/cancelled.
If you share how your SaleRequestDto identifies products (SKU vs productId) and whether you want single-warehouse vs multi-warehouse allocation per order, I can recommend the exact command shape (single command vs per-line) and the minimal changes needed to your event-contracts.

## Me
Great suggestion. When product-service emit explicit reservation success/failure events correlated by saleId. product-service should be able to contact stockService to verify stock level. This is okay by me. For the payment, i would like to create payment-service microservice separately



Reservation-first + separate payment-service: best way to orchestrate createSale
You’re on the right track. With a dedicated payment-service, the clean approach is a Saga / process manager pattern where:
sales-service owns the sale lifecycle
product-service owns inventory (reservation + commit/release)
payment-service owns payment authorization/capture
all coordination is done via commands + outcome events correlated by saleId

1) Avoid “derive warehouseId from product -> stock -> warehouse” in sales-service
   That navigation is internal to product-service and may change. Also, sales-service shouldn’t decide allocation.
   Instead, do this:
   sales-service publishes ReserveStockCommand(saleId, productId, quantity) without warehouseId
   product-service chooses warehouse internally and replies with StockReservedEvent(saleId, productId, warehouseId, quantity) or StockReservationFailedEvent(...)
   If your current command requires warehouseId, change the contract to make it optional (nullable) or introduce a dedicated reservation command without warehouseId.

2) Use explicit reservation success/failure events (correlated by saleId)
   In product-service, it’s totally fine to “contact” StockService internally—this is not a remote call, it’s just calling your own domain/service layer.
   Implementation idea:
   consumer receives reservation command
   it loads candidate stock entries for that product (across warehouses)
   picks a warehouse with enough available
   performs reservation update (atomic)
   publishes success/failure event with saleId
   Important: success/failure event should include saleId + productId + quantity (+ chosen warehouseId on success). This is what lets sales-service progress the saga.
3) Full saga flow (recommended)
   Step A — Create sale + request stock reservation
   sales-service:
   Create Sale in DB with status PENDING_STOCK
   For each sale item publish ReserveStockCommand (or one command with list of items)
   Step B — Stock reserved (or failed)
   product-service:
   On success: publish StockReservedEvent(saleId, productId, warehouseId, quantity)
   On failure: publish StockReservationFailedEvent(saleId, productId, reason)
   sales-service:
   If all items reserved → mark sale STOCK_RESERVED and publish InitiatePaymentCommand(saleId, amount, payment details)
   If any item failed → mark sale REJECTED and publish ReleaseStockReservationCommand(...) for already-reserved items (compensation)
   Step C — Payment result
   payment-service:
   consumes InitiatePaymentCommand
   publishes PaymentSucceededEvent(saleId, paymentId) or PaymentFailedEvent(saleId, reason)
   sales-service:
   on success → publish CommitStockCommand(saleId, productId, warehouseId, quantity) (this is your StockUpdateType.SALE)
   on failure → publish ReleaseReservationCommand(saleId, productId, warehouseId, quantity) and mark sale PAYMENT_FAILED/CANCELLED
   Step D — Commit stock / release reservation
   product-service:
   commit means convert reservation to final sale (decrease reserved, finalize on-hand, etc.)
   release means undo reservation
   Your current StockUpdateType has RESERVATION and SALE but no explicit RELEASE. You’ll need either: add RELEASE_RESERVATION, or treat release as a reservation with negative quantityChange (but your code rejects negative), or add a separate “ReleaseReservationCommand” that your stock service handles explicitly.
   I recommend adding a dedicated release command/type.

4) Concurrency / correctness notes (important for reservations)
   When multiple sales reserve the same stock concurrently, you must ensure reservation is safe. Typical approaches:
   database transaction with row locking on the stock row (SELECT ... FOR UPDATE / JPA locking)
   or optimistic locking with a @Version field on StockEntity
   Without that, two reservations can read the same available stock and both succeed.

5) Minimal changes to support your preference (reservation) cleanly
   Add new events in event-contracts:
   StockReservationRequestedCommand (or reuse StockUpdateCommandEvent with updateType=RESERVATION and nullable warehouseId)
   StockReservedEvent
   StockReservationFailedEvent
   StockReservationReleasedEvent (optional)
   Implement consumer in product-service that calls StockService and emits success/failure.
   Implement a small “orchestrator” in sales-service that listens for those events and triggers payment / commit / release.
   Implement payment-service consuming payment command and emitting payment result events.
