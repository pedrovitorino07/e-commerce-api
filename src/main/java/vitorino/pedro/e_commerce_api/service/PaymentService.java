package vitorino.pedro.e_commerce_api.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.PaymentResponseDTO;
import vitorino.pedro.e_commerce_api.entity.Order;
import vitorino.pedro.e_commerce_api.entity.Payment;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.enums.OrderStatus;
import vitorino.pedro.e_commerce_api.enums.PaymentStatus;
import vitorino.pedro.e_commerce_api.exception.AccessDeniedException;
import vitorino.pedro.e_commerce_api.exception.OrderNotFoundException;
import vitorino.pedro.e_commerce_api.exception.PaymentNotFoundException;
import vitorino.pedro.e_commerce_api.exception.UserNotFoundException;
import vitorino.pedro.e_commerce_api.repository.OrderRepository;
import vitorino.pedro.e_commerce_api.repository.PaymentRepository;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

import java.math.BigDecimal;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        ));
    }

    public PaymentResponseDTO createPayment(Long orderId)
            throws StripeException {

        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Pedido não encontrado"
                        ));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(
                    "Você não pode pagar pedidos de outro usuário"
            );
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Somente pedidos pendentes podem ser pagos"
            );
        }

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(
                                order.getTotalPrice()
                                        .multiply(BigDecimal.valueOf(100))
                                        .longValue()
                        )
                        .setCurrency("brl")
                        .build();

        PaymentIntent paymentIntent =
                PaymentIntent.create(params);

        Payment payment = new Payment();

        payment.setOrder(order);

        payment.setStripePaymentIntentId(
                paymentIntent.getId()
        );

        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment =
                paymentRepository.save(payment);

        return new

                PaymentResponseDTO(
                savedPayment.getId(),
                paymentIntent.getId(),
                paymentIntent.getClientSecret(),
                savedPayment.getStatus()
        );
    }

    @Transactional
    public void handlePaymentSuccess(String paymentIntentId) {

        Payment payment = paymentRepository
                .findByStripePaymentIntentId(paymentIntentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Pagamento não encontrado"
                        ));

        payment.setStatus(PaymentStatus.PAID);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.PAID);

        orderRepository.save(order);
        paymentRepository.save(payment);
    }

    @Transactional
    public void handlePaymentFailure(String paymentIntentId) {

        Payment payment = paymentRepository
                .findByStripePaymentIntentId(paymentIntentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Pagamento não encontrado"
                        ));

        payment.setStatus(PaymentStatus.FAILED);

        paymentRepository.save(payment);
    }

    public PaymentResponseDTO getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Pagamento não encontrado"
                        ));

        return toResponseDTO(payment);
    }

    private PaymentResponseDTO toResponseDTO(Payment payment) {

        return new PaymentResponseDTO(
                payment.getId(),
                payment.getStripePaymentIntentId(),
                null,
                payment.getStatus()
        );
    }

    public PaymentResponseDTO getPaymentByOrder(Long orderId) {

        Payment payment = paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Pagamento não encontrado"
                        )
                );

        return toResponseDTO(payment);
    }

    public void processWebhook(
            String payload,
            String signature
    ) {

    }
}