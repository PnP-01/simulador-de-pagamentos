package br.com.dio.pagamentos.fraud;

import br.com.dio.pagamentos.model.PaymentRequest;

import java.util.Optional;

/**
 * CHAIN OF RESPONSIBILITY: cada elo avalia o pedido; se achar problema, a corrente
 * para e devolve o motivo; senao, repassa para o proximo elo.
 */
public abstract class FraudHandler {
    private FraudHandler next;

    /** Encadeia o proximo elo e o devolve (permite a.linkWith(b).linkWith(c)). */
    public FraudHandler linkWith(FraudHandler next) {
        this.next = next;
        return next;
    }

    public final Optional<String> check(PaymentRequest request) {
        Optional<String> problem = evaluate(request);
        if (problem.isPresent() || next == null) {
            return problem;
        }
        return next.check(request);
    }

    protected abstract Optional<String> evaluate(PaymentRequest request);
}
