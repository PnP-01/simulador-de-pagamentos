package br.com.dio.pagamentos;

import br.com.dio.pagamentos.facade.PaymentFacade;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentResult;
import br.com.dio.pagamentos.model.PaymentStatus;
import br.com.dio.pagamentos.model.PaymentType;
import br.com.dio.pagamentos.observer.AuditLog;
import br.com.dio.pagamentos.observer.AuditLogListener;
import br.com.dio.pagamentos.observer.PaymentEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFacadeTest {
    private PaymentFacade facade;
    private final List<PaymentResult> received = new ArrayList<>();

    @BeforeEach
    void setUp() {
        AuditLog.INSTANCE.clear();
        received.clear();
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        publisher.subscribe(received::add);
        publisher.subscribe(new AuditLogListener());
        facade = new PaymentFacade(PaymentFacade.defaultFraudChain(Set.of("fraudador")), publisher);
    }

    @Test
    void pagamentoAprovadoCalculaValorFinalENotificaObservers() {
        PaymentResult result = facade.pay(
                new PaymentRequest("Bruno", new BigDecimal("100"), PaymentType.CARTAO, LocalTime.of(14, 0)));

        assertEquals(PaymentStatus.APPROVED, result.status());
        assertEquals(new BigDecimal("102.99"), result.finalAmount());
        assertEquals(1, received.size());
        assertEquals(1, AuditLog.INSTANCE.entries().size());
    }

    @Test
    void pagamentoRecusadoTambemNotificaObservers() {
        PaymentResult result = facade.pay(
                new PaymentRequest("fraudador", new BigDecimal("50"), PaymentType.PIX, LocalTime.of(12, 0)));

        assertFalse(result.approved());
        assertEquals(1, received.size());
    }

    @Test
    void singletonDeAuditoriaEUnico() {
        assertSame(AuditLog.INSTANCE, AuditLog.valueOf("INSTANCE"));
    }

    @Test
    void valorInvalidoELancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new PaymentRequest("Ana", BigDecimal.ZERO, PaymentType.PIX, LocalTime.NOON));
    }
}
