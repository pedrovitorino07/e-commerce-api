package vitorino.pedro.e_commerce_api.controller;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.PaymentResponseDTO;
import vitorino.pedro.e_commerce_api.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order/{orderId}")
    public PaymentResponseDTO createPayment(
            @PathVariable Long orderId
    ) throws StripeException {

        return paymentService.createPayment(orderId);
    }

    @GetMapping("/{paymentId}")
    public PaymentResponseDTO getPayment(
            @PathVariable Long paymentId
    ) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping("/order/{orderId}")
    public PaymentResponseDTO getPaymentByOrder(
            @PathVariable Long orderId
    ) {
        return paymentService.getPaymentByOrder(orderId);
    }

    @PostMapping("/webhook")
    public void webhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature")
            String signature
    ) {

        paymentService.processWebhook(
                payload,
                signature
        );
    }
}
