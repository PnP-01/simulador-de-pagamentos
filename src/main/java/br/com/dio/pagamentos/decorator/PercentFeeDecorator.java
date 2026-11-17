package br.com.dio.pagamentos.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Soma uma taxa percentual ao valor (ex.: juros do cartao). */
public class PercentFeeDecorator extends PriceDecorator {
    private final BigDecimal percent;

    public PercentFeeDecorator(PriceComponent inner, double percent) {
        super(inner);
        this.percent = BigDecimal.valueOf(percent);
    }

    @Override
    public BigDecimal total() {
        BigDecimal fee = inner.total().multiply(percent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return inner.total().add(fee);
    }

    @Override
    public String description() {
        return inner.description() + " + taxa " + percent + "%";
    }
}
