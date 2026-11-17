package br.com.dio.pagamentos.strategy;

import br.com.dio.pagamentos.decorator.PercentFeeDecorator;
import br.com.dio.pagamentos.decorator.PriceComponent;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentType;

import java.math.BigDecimal;

public class CreditCardStrategy implements PaymentStrategy {
    @Override
    public PaymentType type() {
        return PaymentType.CARTAO;
    }

    @Override
    public PriceComponent applyCharges(PriceComponent base) {
        return new PercentFeeDecorator(base, 2.99); // taxa do cartao
    }

    @Override
    public String authorize(PaymentRequest request, BigDecimal total) {
        return "Cartao autorizado: R$ " + total + " para " + request.payer();
    }
}
