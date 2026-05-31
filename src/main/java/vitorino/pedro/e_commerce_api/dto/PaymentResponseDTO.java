package vitorino.pedro.e_commerce_api.dto;

import vitorino.pedro.e_commerce_api.enums.PaymentStatus;

public record PaymentResponseDTO(
        Long id,
        String paymentIntentId,
        String clientSecret,
        PaymentStatus status
) {}
