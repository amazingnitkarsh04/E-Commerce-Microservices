# 🛒 Product Service

The **Product Service** manages product data for the e-commerce microservices application.  
It supports product CRUD, category-based filtering, search with pagination, Redis caching, and inter-service communication with **User Service** and **Order Service**.

---

## Tech Stack
- **Java 17**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Redis Cache**
- **Consul (Service Discovery)**
- **RestTemplate / Feign Clients (Inter-service communication)**
- **JUnit + Mockito (Tests)**
- **Maven**

---

## Project Structure
```
src/main/java/com.example.ProductCatalogServiceProxyy
│
├── Config/                  # Redis config, service configs
├── Controller/              # REST Controllers (Product, Search, Category)
├── Clients/                 # Communication with 3rd party Api (Fakestore)
├── Dtos/                    # Request/Response DTOs
├── Exceptions/              # Custom exceptions + handlers
├── Models/                  # Entities (Product, Category)
├── Repository/              # JPA repositories
└── Services/                # Service interfaces & implementations

src/test/java/com.example.ProductCatalogServiceProxyy
├── Controller/              # Controller test cases
├── Repository/              # Repository tests
└── Stubs/                   # Stub data for mocking
```

---

## ⚙️ Running Requirements

### **1️⃣ Start Redis Before Running**
```
redis-server
```

### **2️⃣ Start Consul (Service Discovery)**
```
consul agent -dev
```

### **3️⃣ Run MySQL + configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db1
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.redis.host=localhost
spring.redis.port=6379

spring.cloud.consul.host=localhost
spring.cloud.consul.port=8500
```

---

## API Endpoints

### ** Search Products (With Pagination)**
```
POST /search
```
**Request Body (SearchRequestDto)**:
```json
{
  "query": "mobile",
  "pageNo": 0,
  "pageSize": 10
}
```

---

### **Get All Products**
```
GET /product
```

### **Get Product By ID**
```
GET /product/{id}
```

---

### **Create Product**
```
POST /product
```
Uses category auto-create if not found.

---

### **Update Product (PATCH)**
```
PATCH /product/{id}
```

---

### **Delete Product**
```
DELETE /product/{id}
```

---

### **Get Product Details (Combines with User Service)**
```
GET /product/{uid}/{pid}
```
Used to fetch product with user-specific info (wishlist, previous orders, etc.).

---

### **Check if Product Exists**
```
GET /product/{id}/exists
```

---

### **Get Products by Category**
```
GET /category/{id}
```

---

## Communication With Other Services

### ✔ **User Service**
Used for checking:
- User-specific product info  
- Product recommendations  
- Wishlist flags  

### **Order Service**
Used for:
- Validating product availability  
- Deducting inventory after order  
- Stock check API  

This service provides product data for order creation.

---

## Features Summary
- Full CRUD for products  
- Auto-create categories on product creation  
- Product search with pagination  
- Redis caching for performance  
- Consul-based service discovery  
- Category filter endpoint  
- Product detail combining user + product data  
- Exception handling layer  
- Unit tests for controller, repo, and service  

---

## Tests
Test packages included:
- `controller` → API layer tests  
- `repository` → JPA & DB tests  
- `stubs` → Fake data for mocks  

---

## Future Improvements
- Add ElasticSearch for powerful search  
- Improve caching logic  
- Add rate limiting  
- Add Swagger documentation  

---

## Contributing
Pull requests are welcome!

