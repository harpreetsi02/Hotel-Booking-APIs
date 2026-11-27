# Hotel Booking API (API Versioning Demo)

Spring Boot 4 + Java 21 + PostgreSQL project focused on **API versioning** using
Spring Framework 7's native versioning support.

## Features
- Hotels, Rooms (nested under hotel), Guests: CRUD, validation, pagination
- Bookings with overlap detection, price snapshot and cancel
- **v1 and v2 Booking APIs on the same URL**, selected by `X-API-Version` header
  - v1: flat request/response
  - v2: nested objects, required `numberOfGuests`, price breakdown
- Baseline versioning (`1+`) for unchanged endpoints
- Deprecation / Sunset / Link headers for v1
- Version-neutral service layer (versions only at controller/DTO/mapper edge)

## Tech
Spring Boot 4.1.2, Java 21, Spring Data JPA, PostgreSQL, Lombok, springdoc-openapi

## Run
1. `CREATE DATABASE hotelbooking_db;`
2. Set DB credentials in `application.yml`
3. `./mvnw spring-boot:run`

## Versioning examples
curl -H "X-API-Version: 2" localhost:8080/api/bookings/1
Strategies tried: header, query param, media-type param (one line change in WebConfig).

## Concepts covered
Fixed vs baseline versions, default/unsupported version handling, deprecation headers,
neutral service layer, JOIN FETCH, JPQL overlap query.