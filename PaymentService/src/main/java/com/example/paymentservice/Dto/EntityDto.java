package com.example.paymentservice.Dto;

import lombok.Data;

@Data
public class EntityDto {

    private String order_id;     // Razorpay order id
    private String status;       // captured / failed
    private NotesDto notes;
}
