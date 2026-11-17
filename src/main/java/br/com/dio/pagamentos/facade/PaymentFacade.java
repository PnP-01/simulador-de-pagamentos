package br.com.dio.pagamentos.facade;

import br.com.dio.pagamentos.decorator.BaseAmount;
import br.com.dio.pagamentos.decorator.PriceComponent;
import br.com.dio.pagamentos.fraud.AmountLimitHandler;
import br.com.dio.pagamentos.fraud.BlockedPayerHandler;
import br.com.dio.pagamentos.fraud.FraudHandler;
import br.com.dio.pagamentos.fraud.NightCardHandler;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentResult;
import br.com.dio.pagamentos.model.PaymentStatus;
import br.com.dio.pagamentos.observer.PaymentEventPublisher;
import br.com.dio.pagamentos.strategy.PaymentStrategy;
import br.com.dio.pagamentos.strategy.PaymentStrategyFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

/**
 * FACADE: ponto unico de entrada. Esconde a orquestracao entre
 * Chain (antifraude) -> Factory/Strategy -> Decorator (valor final) -> Observer (eventos).
 */
public class PaymentFacade {
    private final FraudHandler fraudChain;
    private final PaymentEventPublisher publisher;

    public PaymentFacade(FraudHandler fraudChain, PaymentEventPublisher publisher) {
        this.fraudChain = fraudChain;
        this.publisher = publisher;
    }

    /** Monta a corrente de antifraude padrao: bloqueio -> limite -> madrugada. */
    public static FraudHandler defaultFraudChain(Set<String> blockedPayers) {
        FraudHandler first = new BlockedPayerHandler(blockedPayers);
        first.linkWith(new AmountLimitHandler(new BigDecimal("10000")))
             .linkWith(new NightCardHandler());
        return first;
    }

    public PaymentResult pay(PaymentRequest request) {
        PaymentResult result;

        Optional<String> fraud = fraudChain.check(request);
        if (fraud.isPresent()) {
            result = new PaymentResult(request, PaymentStatus.REJECTED, request.amount(),
                    "Recusado pelo antifraude", "Pagamento recusado: " + fraud.get());
        } else {
            PaymentStrategy strategy = PaymentStrategyFactory.create(request.type());
            PriceComponent price = strategy.applyCharges(new BaseAmount(request.amount()));
            result = new PaymentResult(request, PaymentStatus.APPROVED, price.total(),
                    price.description(), strategy.authorize(request, price.total()));
        }

        publisher.publish(result);
        return result;
    }
}
