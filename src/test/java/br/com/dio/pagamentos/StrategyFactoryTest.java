package br.com.dio.pagamentos;

import br.com.dio.pagamentos.decorator.BaseAmount;
import br.com.dio.pagamentos.model.PaymentType;
import br.com.dio.pagamentos.strategy.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class StrategyFactoryTest {

    @Test
    void fabricaEscolheAEstrategiaCerta() {
        assertInstanceOf(PixStrategy.class, PaymentStrategyFactory.create(PaymentType.PIX));
        assertInstanceOf(CreditCardStrategy.class, PaymentStrategyFactory.create(PaymentType.CARTAO));
        assertInstanceOf(BoletoStrategy.class, PaymentStrategyFactory.create(PaymentType.BOLETO));
    }

    @Test
    void cadaEstrategiaCobraDeUmJeito() {
        BaseAmount base = new BaseAmount(new BigDecimal("100"));
        assertEquals(new BigDecimal("99.00"), new PixStrategy().applyCharges(base).total());
        assertEquals(new BigDecimal("102.99"), new CreditCardStrategy().applyCharges(base).total());
        assertEquals(new BigDecimal("103.50"), new BoletoStrategy().applyCharges(base).total());
    }
}
