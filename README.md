# ShortBreaksHub – Backend

This is the **Java Spring Boot backend** for **ShortBreaksHub**, a travel planning website.  
It provides REST APIs for user accounts, itineraries, favorites (likes), and profile management.

---

## 🚀 Tech Stack
- **Java 17+**
- **Spring Boot 3.x** (Web, Data JPA, Validation, Security)
- **PostgreSQL** (via Supabase in cloud)
- **Maven** build tool
- **Cloudinary** (for image hosting)
- **JWT Authentication**
- Deployment: Render (API) + Supabase (DB)

---

## 📂 Project Structure
```
src/main/java/com/shortbreakshub/
├── model/ # JPA entities (User, Itinerary, Favorite, etc.)
├── dto/ # Request/Response DTOs
├── repo/ # Spring Data JPA repositories
├── service/ # Business logic layer
├── controller/ # REST endpoints
└── config/ # Security & application config
```


---

## ⚙️ Setup Instructions

### 1. Prerequisites
- JDK 17+
- Maven 3.8+
- PostgreSQL running (or Supabase)

### 2. Clone & configure
```bash
git clone https://github.com/wonton1979/shortbreaks-backend.git
cd shortbreaks-backend
```

Add secrets in application.properties (⚠️ never commit secrets to GitHub)

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/shortbreaks
spring.datasource.username=your_db_user
spring.datasource.password=your_db_pass

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

cloudinary.cloud-name=xxxx
cloudinary.api-key=xxxx
cloudinary.api-secret=xxxx

jwt.secret=xxxx

```

### 3. Run locally

mvn spring-boot:run

API will be available at http://localhost:8080


🧪 Validation & Transactions

Fields validated with @Min, @Max, @Size, @AssertTrue

Defaults enforced with @PrePersist / @PreUpdate

@Transactional for write ops; @Transactional(readOnly = true) for queries involving lazy loading


📌 Roadmap

✅ User accounts & JWT auth

✅ Itinerary browsing

✅ Favorites (likes)

⬜ Bookmarks (private saves)

⬜ Comments & reviews

⬜ Search & filtering

