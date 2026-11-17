package br.com.dio.pagamentos.fraud;

import br.com.dio.pagamentos.model.PaymentRequest;

import java.math.BigDecimal;
import java.util.Optional;

public class AmountLimitHandler extends FraudHandler {
    private final BigDecimal limit;

    public AmountLimitHandler(BigDecimal limit) {
        this.limit = limit;
    }

    @Override
    protected Optional<String> evaluate(PaymentRequest request) {
        return request.amount().compareTo(limit) > 0
                ? Optional.of("Valor acima do limite de R$ " + limit)
                : Optional.empty();
    }
}
