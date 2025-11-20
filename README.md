# Hospital Management System

A complete **Hospital Management System (HMS)** built with **Spring Boot 3.5.7** for backend, **MySQL** as the database, and dual frontend implementations using **Angular** and **React**.

**Simple Hospital Management System (HMS)**

This repository contains a lightweight Hospital Management System built with Spring Boot. It models three core entities — **Doctor**, **Patient**, and **Appointment** — and provides REST APIs to create, read, update, and delete them, plus query endpoints for appointments by doctor/date and status management.

---

## Table of Contents

* [Features](#features)
* [Tech stack](#tech-stack)
* [Domain model](#domain-model)
* [API Endpoints (summary)](#api-endpoints-summary)
* [Sample JSON payloads](#sample-json-payloads)
* [Running locally](#running-locally)
* [Database](#database)
* [Validation & common errors](#validation--common-errors)
* [Testing](#testing)
* [Troubleshooting](#troubleshooting)
* [Contributing](#contributing)
* [License](#license)

---

## Features

* CRUD for **Doctor**, **Patient**, **Appointment**
* Appointment queries by doctor, date, and status
* Appointment status transitions (e.g., SCHEDULED → COMPLETED → CANCELLED)
* Input validation and basic error responses

---

## Tech stack

* **Backend:** Spring Boot **3.5.7**, Spring Web, Spring Data JPA, Validation
* **Database:** MySQL 8+
* **Frontend:** Angular (latest) and React (latest)
* **Build Tools:** Maven or Gradle
* **Other:** Lombok, Hibernate ORM

---

## Domain model

### Doctor

* `doctor_id` (Long, PK)
* `name` (String)
* `specialization` (String)
* `email` (String)
* `phone` (String)

### Patient

* `patient_id` (Long, PK)
* `name` (String)
* `dob` (LocalDate)
* `gender` (String)
* `email` (String)
* `phone` (String)

### Appointment

* `appointment_id` (Long, PK)
* `date` (LocalDate)
* `time` (LocalTime)
* `doctor` (ManyToOne -> Doctor)
* `patient` (ManyToOne -> Patient)
* `status` (Enum; e.g., SCHEDULED, COMPLETED, CANCELLED)

Relationships: `Appointment` has `@ManyToOne` associations to both `Doctor` and `Patient`.

---

## API Endpoints (summary)

> Base path: `/api` (adjust if your app uses a different prefix)

### Doctors

* `GET /api/doctors` — list all doctors
* `GET /api/doctors/{id}` — get doctor by id
* `POST /api/doctors` — create doctor
* `PUT /api/doctors/{id}` — update doctor
* `DELETE /api/doctors/{id}` — delete doctor

### Patients

* `GET /api/patients` — list all patients
* `GET /api/patients/{id}` — get patient by id
* `POST /api/patients` — create patient
* `PUT /api/patients/{id}` — update patient
* `DELETE /api/patients/{id}` — delete patient

### Appointments

* `GET /api/appointments` — list all appointments
* `GET /api/appointments/{id}` — get appointment by id
* `POST /api/appointments` — create appointment
* `PUT /api/appointments/{id}` — update appointment
* `DELETE /api/appointments/{id}` — delete appointment
* `GET /api/appointments/doctor/{doctorId}` — list appointments for a doctor
* `GET /api/appointments/doctor/{doctorId}?date=YYYY-MM-DD` — list doctor's appointments for a date
* `GET /api/appointments/date/{date}` — list appointments on a date
* `PATCH /api/appointments/{id}/status` — change status (body: `{ "status": "COMPLETED" }`)

---

## Sample JSON payloads

### Create Doctor

```json
{
  "name": "Dr. A. Kumar",
  "specialization": "Cardiology",
  "email": "akumar@hospital.com",
  "phone": "+91-9876543210"
}
```

### Create Patient

```json
{
  "name": "Priya Sharma",
  "dob": "1990-05-25",
  "gender": "F",
  "email": "priya.sharma@example.com",
  "phone": "+91-9123456780"
}
```

### Create Appointment (recommended — reference existing doctor & patient by id)

```json
{
  "date": "2025-11-15",
  "time": "10:30:00",
  "doctor": { "doctor_id": 1 },
  "patient": { "patient_id": 2 },
  "status": "SCHEDULED"
}
```

**Important:** When creating an appointment the `doctor.doctor_id` and `patient.patient_id` **must** be provided (or you must pass separate `doctorId` and `patientId` fields depending on your API design). Missing `patient_id` or `doctor_id` will cause a database constraint error: `Column 'patient_id' cannot be null`.

---

## Running locally

1. Clone the repo:

```bash
git clone <repo-url>
cd Hospital_Management_System
```

2. Configure `application.properties` (or `application.yml`) — example for H2 (dev):

```properties
spring.datasource.url=jdbc:h2:mem:hmsdb;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

For MySQL / Postgres, update the URL, username, password and set a proper `ddl-auto` or use Flyway/Liquibase.

3. Build & run

```bash
./mvnw spring-boot:run
# or
mvn spring-boot:run
```

4. Open API: `http://localhost:8080/api` — use Postman / curl to test endpoints.

---

## Database

* The project uses JPA/Hibernate. For production use an external RDBMS (MySQL/Postgres) and configure connection properties.
* If you use `spring.jpa.hibernate.ddl-auto=update`, JPA will create/update schema automatically. For safer production migrations, use Flyway or Liquibase.

---

## Validation & common errors

* **`Column 'patient_id' cannot be null` / `patient id is required`** — Occurs when creating an `Appointment` without linking an existing `Patient`. Fix by including `{ "patient": { "patient_id": <id> } }` or pass `patientId` and resolve it server-side.
* **`doctorId and patientId are required`** — If service-layer checks enforce presence of referenced IDs before saving.
* Ensure entity IDs are `null` when you want JPA to generate them for inserts (e.g., `request.setAppointment_id(null)` before `save`).

---

## Testing

* Add unit tests for services (mock repositories) and integration tests (use H2).
* Example tools: JUnit 5, Mockito, Spring Boot Test.

---

## Troubleshooting

* If you receive `UnresolvedAddressException` during `git push` or remote operations, check your remote URL and network.
* If app fails to start with schema errors, enable `debug` in Spring Boot to get the condition evaluation report.

---

## Contributing

1. Fork the repo
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes and open a pull request

Please follow conventional commits and include tests for new behavior.

---

## License

MIT License — see `LICENSE` file.

---

If you'd like, I can also:

* generate OpenAPI (Swagger) documentation for the existing controllers,
* provide Postman collection or curl examples for every endpoint,
* add DTOs and request/response examples,
* or create a minimal frontend (React) to interact with the API.

Tell me which of the above you'd like next.
