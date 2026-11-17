package br.com.dio.pagamentos;

import br.com.dio.pagamentos.decorator.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DecoratorTest {

    @Test
    void taxaPercentualSomaAoValor() {
        PriceComponent price = new PercentFeeDecorator(new BaseAmount(new BigDecimal("100")), 2.99);
        assertEquals(new BigDecimal("102.99"), price.total());
    }

    @Test
    void decoratorsPodemSerEmpilhados() {
        PriceComponent price = new CashbackDecorator(
                new FixedFeeDecorator(new BaseAmount(new BigDecimal("100")), 3.50), 10);
        // 100 + 3,50 = 103,50 ; cashback de 10% = 10,35 ; total = 93,15
        assertEquals(new BigDecimal("93.15"), price.total());
        assertTrue(price.description().contains("tarifa") && price.description().contains("cashback"));
    }
}
