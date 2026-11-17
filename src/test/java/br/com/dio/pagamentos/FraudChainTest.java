package br.com.dio.pagamentos;

import br.com.dio.pagamentos.facade.PaymentFacade;
import br.com.dio.pagamentos.fraud.FraudHandler;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FraudChainTest {
    private final FraudHandler chain = PaymentFacade.defaultFraudChain(Set.of("fraudador"));

    private PaymentRequest req(String payer, String amount, PaymentType type, int hour) {
        return new PaymentRequest(payer, new BigDecimal(amount), type, LocalTime.of(hour, 0));
    }

    @Test
    void pedidoNormalPassaPorTodaACorrente() {
        assertFalse(chain.check(req("Ana", "100", PaymentType.PIX, 14)).isPresent());
    }

    @Test
    void bloqueiaPagadorNaListaNegra() {
        assertTrue(chain.check(req("fraudador", "10", PaymentType.PIX, 14)).isPresent());
    }

    @Test
    void bloqueiaValorAcimaDoLimite() {
        assertTrue(chain.check(req("Ana", "10000.01", PaymentType.PIX, 14)).isPresent());
    }

    @Test
    void bloqueiaCartaoAltoNaMadrugada() {
        assertTrue(chain.check(req("Ana", "2500", PaymentType.CARTAO, 3)).isPresent());
        assertFalse(chain.check(req("Ana", "2500", PaymentType.PIX, 3)).isPresent());
    }
}
