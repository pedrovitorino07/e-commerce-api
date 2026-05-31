package vitorino.pedro.e_commerce_api.entity;

import jakarta.persistence.*;
import lombok.*;
import vitorino.pedro.e_commerce_api.enums.PaymentStatus;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stripePaymentIntentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne
    private Order order;
}
