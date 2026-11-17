package br.com.dio.pagamentos.strategy;

import br.com.dio.pagamentos.decorator.CashbackDecorator;
import br.com.dio.pagamentos.decorator.PriceComponent;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentType;

import java.math.BigDecimal;

public class PixStrategy implements PaymentStrategy {
    @Override
    public PaymentType type() {
        return PaymentType.PIX;
    }

    @Override
    public PriceComponent applyCharges(PriceComponent base) {
        return new CashbackDecorator(base, 1.0); // Pix tem 1% de cashback
    }

    @Override
    public String authorize(PaymentRequest request, BigDecimal total) {
        return "Pix de R$ " + total + " confirmado na hora para " + request.payer();
    }
}
