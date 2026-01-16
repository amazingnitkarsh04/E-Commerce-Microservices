package com.example.paymentservice.PaymentGateway;

import com.example.paymentservice.PaymentGateway.PaymentGateway;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements PaymentGateway {

    @Value("${stripe.key.secret}")
    private String apiKey;

    @Override
    public String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount) {
        try {
            // Set Stripe API key
            Stripe.apiKey = apiKey;

            // Stripe expects amount in cents -> multiply by 100
            long amountInCents = amount * 100;

            // Create Price object for the payment
            Price price = createPrice(amountInCents);

            // Create a Payment Link
            PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                    .addLineItem(
                            PaymentLinkCreateParams.LineItem.builder()
                                    .setPrice(price.getId())
                                    .setQuantity(1L)
                                    .build()
                    )
                    .setAfterCompletion(
                            PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(
                                            PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                    .setUrl("https://scaler.com/") // redirect page
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            PaymentLink link = PaymentLink.create(params);
            return link.getUrl();

        } catch (StripeException ex) {
            throw new RuntimeException("Stripe payment link creation failed: " + ex.getMessage(), ex);
        }
    }

    // Helper method to create a Stripe price
    private Price createPrice(Long amountInCents) {
        try {
            PriceCreateParams params = PriceCreateParams.builder()
                    .setCurrency("usd") // You are in India. Use INR.
                    .setUnitAmount(amountInCents)
                    .setProductData(
                            PriceCreateParams.ProductData.builder()
                                    .setName("Order Payment")
                                    .build()
                    )
                    .build();

            return Price.create(params);

        } catch (StripeException ex) {
            throw new RuntimeException("Stripe price creation failed: " + ex.getMessage(), ex);
        }
    }
}
