# 💳 Payment Service (PaymentGateway)

The **Payment Service** (PaymentGateway) handles payment initiation, webhook verification, and communicates with the **Order Service** to update order status.  
It supports **Razorpay** and **Stripe**, uses **webhooks**, and integrates with other microservices through **RestTemplate** and **Consul**.

---

##  Tech Stack
- **Java 17**
- **Spring Boot**
- **Spring MVC**
- **MySQL**
- **Razorpay API**
- **Stripe API**
- **RestTemplate (inter-service communication)**
- **Consul Service Discovery**
- **Gson**
- **Maven**

---

##  Project Structure
```
src/main/java/com.example.paymentservice
│
├── Controllers/             # PaymentController
├── Dto/                     # InitiatePaymentDto, RazorpayWebhookDto
├── Service/                 # PaymentService (business logic)
├── PaymentGateway/          # Provider-specific integration (Stripe/Razorpay)
└── Config/                  # Consul config, RestTemplate config
```

##  Running Requirements

### **1️⃣ Configure MySQL**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/paymentdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

### **2️⃣ Start Consul**
```
consul agent -dev
```

---

### **3️⃣ Razorpay Configuration**
Add to your environment or application.properties:
```properties
razorpay.key_id=your_key_id
razorpay.key_secret=your_key_secret
razorpay.webhook_secret=your_webhook_secret
```

---

### **4️⃣ Stripe Configuration**
```properties
stripe.secret_key=your_stripe_key
```

---

### **5️⃣ Run the Service**
```
mvn spring-boot:run
```

---

# 🛠️ API Endpoints

Base URL: `/payments`

---

##  **1. Initiate Payment (Razorpay / Stripe)**  
```
POST /payments/initiate/{provider}
```

### **Path Variable**
| Name | Description |
|------|-------------|
| provider | razorpay or stripe |

### **Request Body (InitiatePaymentDto)**
```json
{
  "orderId": "ORD123",
  "email": "test@mail.com",
  "phoneNumber": "9999999999",
  "amount": 700
}
```

### **Response**
Returns a **payment link** (Razorpay) or **payment session URL** (Stripe).

---

##  **2. Razorpay Webhook**
```
POST /payments/webhook
```

### Webhook Flow
1. Razorpay sends the webhook to `/payments/webhook`
2. Signature is verified  
3. Extract payment status + orderId from webhook payload  
4. Notify Order Service:

```
POST http://order-service/orders/update-status
{
  "orderId": "ORD123",
  "status": "captured"
}
```

---

#  Communication with Other Services

### ✔ **Order Service**
After webhook verification:
- Sends updated payment status  
- Example statuses: `captured`, `failed`, `created`  

### ✔ **Consul**
Registered as:
```
payment-service
```

---

# Payment Gateway Integrations

### ✔ Razorpay
- Creates payment order  
- Generates payment link  
- Webhook signature verification  
- Notes mapping: `orderId`

### ✔ Stripe
- Creates payment session  
- Returns hosted checkout link  
- (Webhook can be added the same way)

---

## Features Summary
- Razorpay + Stripe integration  
- Payment initiation API  
- Webhook verification  
- Order Service status updates  
- Consul service registry  
- JSON conversion with Gson  
- Uses RestTemplate for internal communication  

---

## Future Improvements
- Add Stripe webhook  
- Save payment history  
- Add refund operations  
- Retry if Order Service fails  
- Add Swagger documentation  

---

## 🤝 Contributing
Pull requests are welcome!

