package br.com.dio.pagamentos.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** DECORATOR (componente concreto): o valor original informado. */
public class BaseAmount implements PriceComponent {
    private final BigDecimal amount;

    public BaseAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal total() {
        return amount;
    }

    @Override
    public String description() {
        return "Valor R$ " + amount;
    }
}
