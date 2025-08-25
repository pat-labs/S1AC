package com.pat.inventory.infrastructure.rest.dto;

public record BillTicketRequest(
        double totalPrice,
        String expeditedAt) {
}