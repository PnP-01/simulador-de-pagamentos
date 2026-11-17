package br.com.dio.pagamentos.fraud;

import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentType;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;

/** Cartao de valor alto entre 00:00 e 05:00 e suspeito. */
public class NightCardHandler extends FraudHandler {
    private static final BigDecimal NIGHT_LIMIT = new BigDecimal("2000");

    @Override
    protected Optional<String> evaluate(PaymentRequest request) {
        boolean night = request.time().isBefore(LocalTime.of(5, 0));
        boolean highCard = request.type() == PaymentType.CARTAO
                && request.amount().compareTo(NIGHT_LIMIT) > 0;
        return night && highCard
                ? Optional.of("Cartao acima de R$ 2000 na madrugada")
                : Optional.empty();
    }
}
