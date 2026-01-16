package com.example.paymentservice.PaymentGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayStrategyChooser {

    @Autowired
    private RazorPayPaymentGateway razorPayPaymentGateway;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    /**
     * Choose based on provider string
     */
    public PaymentGateway getBestPaymentGateway(String provider) {
        if (provider == null) return razorPayPaymentGateway;

        switch (provider.toLowerCase()) {
            case "stripe":
                return stripePaymentGateway;

            case "razorpay":
            default:
                return razorPayPaymentGateway;
        }
    }
}
