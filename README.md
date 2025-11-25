# OpenDesk – Open-Source Ticketing & Helpdesk System  
**Modern, lightweight, full-stack ticketing system built with Spring Boot 3 + Angular 18 + PostgreSQL**  
Single Docker image · Ready for staging & production · 100% free & open-source (MIT)

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.3+-green)](https://spring.io/projects/spring-boot)
[![Angular 18](https://img.shields.io/badge/Angular-18-red)](https://angular.dev)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-blue?logo=postgresql)](https://www.postgresql.org/)

## Features (MVP & beyond)

- Ticket creation, assignment, status/phase workflow  
- Priority + due dates + SLA tracking  
- Customer feedback & rating (1–5 stars)  
- File attachments (stored on disk or S3)  
- Internal comments vs customer replies  
- Full audit trail (TicketHistory)  
- Role-based access control (RBAC) with granular authorities  
- Performance metrics dashboard for agents  
- Soft delete & logical separation of data  
- Responsive Angular frontend (Material Design)  
- REST + Server-Sent Events (real-time updates)  
- Single Docker image deployment (zero-config on any host)

## Tech Stack

| Layer               | Technology                                      |
|---------------------|-------------------------------------------------|
| Backend             | Spring Boot 3.3+, Spring Data JPA, Spring Security, Flyway |
| Frontend            | Angular 18 + TypeScript + Angular Material      |
| Database            | PostgreSQL 16+                                  |
| Build               | Maven (backend) + npm/angular-cli (frontend)    |
| Packaging           | Multi-stage Docker (OpenJDK 21 → Nginx → final image) |
| Real-time (optional)| Server-Sent Events (SSE)                        |
| File storage        | Local volume or S3 (configurable)               |

## Quick Start (Docker – recommended)

```bash
# 1. Clone the repo
git clone https://github.com/Caesar-final/opendesk.git
cd opendesk

# 2. Copy example env and adjust if needed
cp .env.example .env

# 3. Start everything with a single command
docker compose up -d

# Application will be available at:
#   Frontend : http://localhost:8080
#   Backend  : http://localhost:8080/api
#   PostgreSQL on port 5432
```

Default users (created on first start):

| Username   | Password | Role          |
|------------|----------|---------------|
| admin      | admin123 | Administrator |
| agent      | agent123 | Support Agent |
| user       | user123  | End User      |

## Manual Build & Run (development)

### Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
# → http://localhost:8081
```

### Frontend (Angular)

```bash
cd frontend
npm install
npm start
# → http://localhost:4200 (proxied to backend)
```

## Production Deployment (single image)

```bash
# Build the optimized production image (~180 MB)
docker build -t opendesk:latest .

# Run with persistent data
docker run -d \
  --name opendesk \
  -p 8080:8080 \
  -v opendesk-data:/data \
  -v opendesk-attachments:/attachments \
  --restart unless-stopped \
  opendesk:latest
```

All static Angular files are served directly by Spring Boot in production mode – no separate Nginx needed.

## Configuration (environment variables)

| Variable                     | Default          | Description                                    |
|------------------------------|------------------|------------------------------------------------|
| SPRING_DATASOURCE_URL        | (docker compose) | PostgreSQL JDBC URL                            |
| ATTACHMENT_STORAGE           | local            | `local` or `s3`                                |
| ATTACHMENT_PATH              | /attachments     | Local folder when storage=local                |
| AWS_S3_BUCKET                | -                | Required when storage=s3                       |
| SERVER_SERVLET_CONTEXT_PATH  | /                | Base path (useful behind reverse proxy)        |

See `.env.example` for the complete list.

## Database Migrations

Flyway handles everything automatically. Migrations are located in:

```
backend/src/main/resources/db/migration/
```

Just add a new `V__*.sql` file and restart – Flyway will apply it.

## Contributing

We love contributions!  
1. Fork the repo  
2. Create your feature branch (`git checkout -b feature/amazing-thing`)  
3. Commit your changes (`git commit -am 'Add amazing thing'`)  
4. Push to the branch (`git push origin feature/amazing-thing`)  
5. Open a Pull Request

Please make sure tests pass:

```bash
# Backend tests
./mvnw test

# Frontend tests
cd frontend && npm run test
```

## License

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

## Support the Project

If you like OpenDesk, give us a star!  
Found a bug or have a feature request? Open an issue – we respond fast.

---
**OpenDesk – Because every team deserves a clean, fast, and free ticketing system.**  

Made with ❤️ by open-source enthusiasts
