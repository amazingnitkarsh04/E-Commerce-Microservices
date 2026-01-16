package com.example.paymentservice.PaymentGateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RazorPayPaymentGateway implements PaymentGateway {

    @Autowired
    private RazorpayClient razorpayClient;

    @Override
    public String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount) {
        try {

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", false);
            paymentLinkRequest.put("expire_by", 1837023696);

            // IMPORTANT: Store your orderId inside metadata (NOTES)
            JSONObject notes = new JSONObject();
            notes.put("yourOrderId", orderId);   // <-- THIS FIXES YOUR ISSUE
            paymentLinkRequest.put("notes", notes);

            paymentLinkRequest.put("reference_id", orderId + "-" + System.currentTimeMillis());
            paymentLinkRequest.put("description", "Payment for order #" + orderId);

            JSONObject customer = new JSONObject();
            customer.put("email", email);
            customer.put("name", "Adarsh Patel");
            customer.put("contact", phoneNumber);
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("reminder_enable", true);

            // You will replace this soon with ngrok webhook URL
            paymentLinkRequest.put("callback_url", "https://www.scaler.com");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
            return paymentLink.get("short_url").toString();

        } catch (RazorpayException r) {
            throw new RuntimeException(r);
        }
    }
}
