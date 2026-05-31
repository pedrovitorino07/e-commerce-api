package vitorino.pedro.e_commerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vitorino.pedro.e_commerce_api.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);
    Optional<Payment> findByOrderId(Long orderId);
}
