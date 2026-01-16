package com.example.paymentservice.Service;

import com.example.paymentservice.PaymentGateway.PaymentGateway;
import com.example.paymentservice.PaymentGateway.PaymentGatewayStrategyChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentGatewayStrategyChooser paymentGatewayStrategyChooser;

    public String initiatePayment(String provider,
                                  String orderId,
                                  String email,
                                  String phoneNumber,
                                  Long amount) {

        // Get correct payment gateway (razorpay / stripe)
        PaymentGateway paymentGateway =
                paymentGatewayStrategyChooser.getBestPaymentGateway(provider.toLowerCase());

        // Generate payment link
        return paymentGateway.generatePaymentLink(orderId, email, phoneNumber, amount);
    }
}
