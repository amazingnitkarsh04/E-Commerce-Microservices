package com.example.paymentservice.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {

    private String orderId;

    private String email;

    private String phoneNumber;

    private Long amount;
}
