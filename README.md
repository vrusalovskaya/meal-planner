# Meal Planner

A backend-oriented **Meal Planner** application built with **Spring Boot** and **Angular**.

The project provides REST APIs for managing ingredients, dishes, and menus, and focuses on clear domain modeling and layered backend architecture.

---

## Project Scope

The application allows:

- Managing ingredients
- Creating dishes with ingredients, quantities, and units
- Creating menus with meals grouped by meal type
- Accessing data through RESTful endpoints

---

## Technology Stack

### Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL / MySQL
- Maven

### Frontend
- Angular
- TypeScript
- HTML / CSS

---

## Architecture Overview

The backend follows a layered structure:

```text
Controller → Service → Repository → Database
```

- **Controllers** handle HTTP requests and responses  
- **Services** contain application logic  
- **Repositories** manage persistence  
- **DTOs** are used to transfer data between layers  

---

## Domain Model

### Ingredient
- name  

### Dish
- name  
- list of ingredients  
- quantity per ingredient  
- unit of measurement  

### Menu
- collection of dishes grouped by meal type  

### Enums
- `MealType` (e.g. BREAKFAST, LUNCH, DINNER)  
- `Unit` (e.g. GRAM, ML, PIECE)  

---

## API Overview
**Base path:**
```text
/api/v1
```
### Ingredients
- `POST /api/ingredients`
- `GET /api/ingredients`
- `GET /api/ingredients/{id}`
- `PUT /api/ingredients/{id}`
- `DELETE /api/ingredients/{id}`

### Dishes
- `POST /api/dishes`
- `GET /api/dishes`
- `GET /api/dishes/{id}`
- `PUT /api/dishes/{id}`
- `DELETE /api/dishes/{id}`

### Menus
- `POST /api/menus`
- `GET /api/menus`
- `GET /api/menus/{id}`

### Project Structure
```text
src/main/java/com/app/mealplanner
├── controllers
├── services
├── repositories
├── entities
├── dtos
└── common/enums
```

---

## Running the Application
### Backend
```bash
mvn clean install
mvn spring-boot:run
```

Backend runs on:
```text
http://localhost:8080
```

### Frontend
```bash
cd client
npm install
ng serve
```

Frontend runs on:
```text
http://localhost:4200
```

---
## Configuration

Example `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/meal_planner
spring.datasource.username=postgres
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


