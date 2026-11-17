package br.com.dio.pagamentos.model;

import java.math.BigDecimal;

/** Resultado do processamento de um pagamento. */
public record PaymentResult(PaymentRequest request,
                            PaymentStatus status,
                            BigDecimal finalAmount,
                            String description,
                            String message) {

    public boolean approved() {
        return status == PaymentStatus.APPROVED;
    }
}
