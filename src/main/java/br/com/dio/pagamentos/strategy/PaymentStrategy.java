package br.com.dio.pagamentos.strategy;

import br.com.dio.pagamentos.decorator.PriceComponent;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentType;

import java.math.BigDecimal;

/** STRATEGY: cada forma de pagamento sabe cobrar (usando Decorator) e autorizar. */
public interface PaymentStrategy {
    PaymentType type();

    /** Aplica as cobrancas/descontos proprios desta forma de pagamento. */
    PriceComponent applyCharges(PriceComponent base);

    /** Mensagem de autorizacao especifica da forma de pagamento. */
    String authorize(PaymentRequest request, BigDecimal total);
}
