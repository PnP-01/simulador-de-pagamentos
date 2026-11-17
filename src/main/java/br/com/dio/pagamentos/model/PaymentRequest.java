package br.com.dio.pagamentos.model;

import java.math.BigDecimal;
import java.time.LocalTime;

/** Pedido de pagamento (imutavel). */
public record PaymentRequest(String payer, BigDecimal amount, PaymentType type, LocalTime time) {

    public PaymentRequest {
        if (payer == null || payer.isBlank()) {
            throw new IllegalArgumentException("Pagador obrigatorio");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        if (type == null) {
            throw new IllegalArgumentException("Tipo de pagamento obrigatorio");
        }
        if (time == null) {
            time = LocalTime.now();
        }
    }
}
