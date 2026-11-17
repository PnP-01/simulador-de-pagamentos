package br.com.dio.pagamentos.strategy;

import br.com.dio.pagamentos.decorator.FixedFeeDecorator;
import br.com.dio.pagamentos.decorator.PriceComponent;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentType;

import java.math.BigDecimal;

public class BoletoStrategy implements PaymentStrategy {
    @Override
    public PaymentType type() {
        return PaymentType.BOLETO;
    }

    @Override
    public PriceComponent applyCharges(PriceComponent base) {
        return new FixedFeeDecorator(base, 3.50); // tarifa de emissao
    }

    @Override
    public String authorize(PaymentRequest request, BigDecimal total) {
        return "Boleto de R$ " + total + " gerado para " + request.payer() + " (compensa em ate 3 dias uteis)";
    }
}
