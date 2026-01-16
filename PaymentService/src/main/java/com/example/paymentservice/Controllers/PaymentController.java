package com.example.paymentservice.Controllers;

import com.example.paymentservice.Dto.InitiatePaymentDto;
import com.example.paymentservice.Dto.RazorpayWebhookDto;
import com.example.paymentservice.Service.PaymentService;
import com.google.gson.Gson;
import com.razorpay.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final String WEBHOOK_SECRET = "your_webhook_secret"; // <-- ADD THIS

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/initiate/{provider}")
    public String initiatePayment(@PathVariable String provider, @RequestBody InitiatePaymentDto initiatePaymentDto) {
        return paymentService.initiatePayment(provider,
                initiatePaymentDto.getOrderId(),
                initiatePaymentDto.getEmail(),
                initiatePaymentDto.getPhoneNumber(),
                initiatePaymentDto.getAmount());
    }

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody RazorpayWebhookDto webhook, @RequestHeader("X-Razorpay-Signature") String signature) {
        // Convert DTO back to JSON string for verification
        String payload = new Gson().toJson(webhook);

        // Verify Razorpay webhook signature
        if (!verifySignature(payload, signature)) {
            throw new RuntimeException("Invalid Razorpay Webhook Signature!");
        }

        // Extract *YOUR* Order ID from notes
        String orderId = webhook.getPayload()
                .getPayment()
                .getEntity()
                .getNotes()
                .getYourOrderId();
        // Extract Razorpay payment status
        String status = webhook.getPayload()
                .getPayment()
                .getEntity()
                .getStatus();

        Map<String, String> body = new HashMap<>();
        body.put("orderId", orderId);
        body.put("status", status);

        restTemplate.postForObject(
                "http://order-service/orders/update-status",
                body,
                String.class
        );

    }

    // Signature verification helper
    private boolean verifySignature(String payload, String signature) {
        try {
            Utils.verifyWebhookSignature(payload, signature, WEBHOOK_SECRET);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
