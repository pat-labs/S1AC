package com.pat.s1ac.infrastructure.rest.dto;

public record BillTicketRequest(
        double totalPrice,
        String expeditedAt) {
}