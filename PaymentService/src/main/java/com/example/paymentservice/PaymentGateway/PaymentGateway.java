package com.example.paymentservice.PaymentGateway;

public interface PaymentGateway {

    String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount);

}
