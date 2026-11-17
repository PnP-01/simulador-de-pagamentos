package br.com.dio.pagamentos.fraud;

import br.com.dio.pagamentos.model.PaymentRequest;

import java.util.Optional;
import java.util.Set;

public class BlockedPayerHandler extends FraudHandler {
    private final Set<String> blocked;

    public BlockedPayerHandler(Set<String> blocked) {
        this.blocked = Set.copyOf(blocked);
    }

    @Override
    protected Optional<String> evaluate(PaymentRequest request) {
        return blocked.contains(request.payer())
                ? Optional.of("Pagador bloqueado")
                : Optional.empty();
    }
}
