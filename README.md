# 🚀 SyncPoint: Enterprise Resource & Audit Platform

SyncPoint is a cloud-native, multi-tenant SaaS application designed to manage high-value enterprise assets with strict data isolation and regulatory compliance.

---

## 🎯 Project Intent

This project serves as a showcase for **Senior Full-Stack Engineering principles**, focusing on:

- **Passive Security:** Multi-tenancy enforced at the database layer.
- **Scalability:** Event-driven architecture using Kafka.
- **Resilience:** Distributed caching with Redis and robust integration testing via Testcontainers.

---

## 🏗️ System Architecture

### 1. Multi-Tenant Strategy (Isolation)
SyncPoint uses a **Shared Schema** approach. To prevent data leakage:

- **TenantContext:** Uses `ThreadLocal` to store the active `tenant_id` per request.
- **Hibernate Filters:** Automatically injects `WHERE tenant_id = ?` into all SQL queries using **Aspect-Oriented Programming (AOP)**.

### 2. Event-Driven Audit Engine (Asynchronous)
To maintain high performance, user actions (e.g., "Deleting an Asset") are decoupled from compliance logging:

1. The **Primary Transaction** updates the MySQL database.
2. An **Audit Event** is published to Apache Kafka.
3. An **Audit Consumer** processes the log and stores it for long-term archival without blocking the user's UI.

---

## 🛠️ Technical Stack

### Backend (Java / Spring Boot)
- **Spring Security 6:** JWT authentication with custom claims for multi-tenancy.
- **Spring Data JPA:** Optimized for MySQL with connection pooling.
- **Redis:** API Rate Limiting (Bucket4j) and global metadata caching.
- **Kafka:** Distributed messaging for background audit tasks.

### Frontend (React / Redux)
- **Redux Toolkit:** State normalization using `createEntityAdapter` for $O(1)$ lookups.
- **React Virtualization:** `react-window` for rendering 10k+ asset records without DOM lag.
- **Performance:** Advanced memoization (`useMemo`, `useCallback`) to minimize re-renders.

---

## 📂 Project Folder Structure (com.project.syncpoint)
```
├── common/             # Shared BaseEntities, Exceptions, and Utilities
├── config/             # Infrastructure (Security, Redis, Kafka, JPA)
├── features/           # Domain Logic (identity, inventory, audit)
└── infrastructure/     # Multi-tenancy filters and JWT interceptors
```


---

## 🧪 Testing Philosophy

- **Integration Tests:** Powered by Testcontainers, spinning up real Docker containers for MySQL, Kafka, and Redis during the build process.
- **Mocking:** Mockito for isolated unit testing of complex business rules.
- **No reliance on local databases** for testing.

---

## 🚀 Deployment & DevOps

- **Containerization:** Multi-stage Docker builds for optimized image sizes.
- **CI/CD:** GitHub Actions runs the Testcontainers suite on every Pull Request.
- **Monitoring:** Spring Boot Actuator integrated for real-time health metrics.

---

## 🔧 Local Development Setup

### 1. Spin up Infrastructure
```bash
docker-compose up -d
```

### 2. Configure Secrets

Create a .env file based on .env.example with your JWT_SECRET and DB_PASSWORD.

### 3. Run Application
```
# Backend
mvn spring-boot:run

# Frontend
npm run dev
```
---
## 🛠️ Techno-Functional Roadmap
- **Project Architecture & Package Design**


- **Infrastructure Definition (Docker / MySQL / Kafka)**

- **Next Step: Implement ```PersistenceConfig.java``` and ```BaseEntity``` to lock in the database isolation**