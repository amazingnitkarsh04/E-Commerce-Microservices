# 👤 User Service

The **User Service** is responsible for authentication, token validation, and providing user details to other microservices.  
It uses **JWT**, **BCrypt**, **Kafka**, **Spring Security**, and **MySQL**.  
Signup and login require ** email + password**.

---

## Tech Stack
- **Java 17**
- **Spring Boot**
- **Spring MVC**
- **Spring Security**
- **JWT Authentication**
- **BCrypt Password Encoder**
- **MySQL**
- **Kafka**
- **Consul Service Discovery**
- **Maven**

---

## Project Structure
```
src/main/java/com.example.UserService
│
├── Config/                  # JWT, Security config, Consul config
├── Controllers/             # AuthController + UserController
├── Dtos/                    # LoginRequestDto, SignupRequestDto, UserDto, ValidateRequestDto
├── Models/                  # User, Role
├── Repository/              # User repository
├── Services/                # AuthService + UserService
└── Kafka/                   # Kafka producer
```  

---

## Running Requirements

### **1️⃣ Start Kafka**
```bash
zookeeper-server-start.sh config/zookeeper.properties
kafka-server-start.sh config/server.properties
```

### **2️⃣ Start Consul**
```bash
consul agent -dev
```

### **3️⃣ Configure MySQL in application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### **4️⃣ Run the service**
```
mvn spring-boot:run
```

---

# API Endpoints

---

## **AUTHENTICATION APIs**  
Base URL: `/auth`

---

### **Signup**
```
POST /auth/signup
```

**Request Body (SignupRequestDto)**  
```json
{
  "email": "test@mail.com",
  "password": "1234"
}
```

**Response**
```json
{
  "email": "test@mail.com",
  "roles": ["USER"]
}
```

---

### **Login**
```
POST /auth/login
```

**Request Body**
```json
{
  "email": "test@mail.com",
  "password": "1234"
}
```

✔ Returns `UserDto`  
✔ Returns JWT token in headers  
✔ Uses BCrypt for password matching  

**Response Header Example**
```
token: <jwt-token>
```

---

### **Validate Token**
```
POST /auth/validate
```

**Request Body**
```json
{
  "token": "<jwt-token>",
  "userId": 1
}
```

**Response**
```json
true
```

Used by Order Service + API Gateway.

---

# USER APIs  
Base URL: `/users`

---

### **Get User Details**
```
GET /users/{id}
```

Response:
```json
{
  "email": "test@mail.com",
  "roles": ["USER"]
}
```

---

### **Check User Exists**
```
GET /users/{id}/exists
```

Response:
```
true / false
```

Used by Product Service and Order Service.

---

## Communication With Other Services

### ✔ **Order Service**
- Validates user ID via `/users/{id}/exists`
- Validates token via `/auth/validate`

### ✔ **Product Service**
- Fetches user roles or profile for personalized product data

### ✔ **Kafka Events**
Used for:
- Logging auth events  
- Broadcasting user creation  
- Security tracking  

---

## Features Summary
- Signup with email & password only  
- Login with JWT token response  
- BCrypt password encryption  
- Token validation endpoint  
- Role-based user access  
- Kafka event publishing  
- Consul service registry  
- User details for microservice communication  

---

## 📈 Future Improvements
- Add refresh tokens  
- Add logout & token blacklist  
- Add user profile management  
- Add rate limiting for login  
- Add Swagger documentation  

---

## 🤝 Contributing
Pull requests are welcome!

