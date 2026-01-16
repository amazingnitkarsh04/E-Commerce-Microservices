package com.example.paymentservice.Dto;

import lombok.Data;

@Data
public class RazorpayWebhookDto {

    private String event;
    private PayloadDto payload;
}
