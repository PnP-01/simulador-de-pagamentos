package br.com.dio.pagamentos.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Subtrai um percentual do valor (desconto/cashback). */
public class CashbackDecorator extends PriceDecorator {
    private final BigDecimal percent;

    public CashbackDecorator(PriceComponent inner, double percent) {
        super(inner);
        this.percent = BigDecimal.valueOf(percent);
    }

    @Override
    public BigDecimal total() {
        BigDecimal discount = inner.total().multiply(percent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return inner.total().subtract(discount);
    }

    @Override
    public String description() {
        return inner.description() + " - cashback " + percent + "%";
    }
}
