package br.com.dio.pagamentos.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Soma uma taxa fixa ao valor (ex.: tarifa de emissao do boleto). */
public class FixedFeeDecorator extends PriceDecorator {
    private final BigDecimal fee;

    public FixedFeeDecorator(PriceComponent inner, double fee) {
        super(inner);
        this.fee = BigDecimal.valueOf(fee).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal total() {
        return inner.total().add(fee);
    }

    @Override
    public String description() {
        return inner.description() + " + tarifa R$ " + fee;
    }
}
