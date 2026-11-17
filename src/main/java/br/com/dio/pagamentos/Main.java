package br.com.dio.pagamentos;

import br.com.dio.pagamentos.facade.PaymentFacade;
import br.com.dio.pagamentos.model.PaymentRequest;
import br.com.dio.pagamentos.model.PaymentResult;
import br.com.dio.pagamentos.model.PaymentType;
import br.com.dio.pagamentos.observer.AuditLog;
import br.com.dio.pagamentos.observer.AuditLogListener;
import br.com.dio.pagamentos.observer.NotificationListener;
import br.com.dio.pagamentos.observer.PaymentEventPublisher;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        publisher.subscribe(new NotificationListener(System.out));
        publisher.subscribe(new AuditLogListener());

        PaymentFacade facade = new PaymentFacade(
                PaymentFacade.defaultFraudChain(Set.of("fraudador")), publisher);

        List<PaymentRequest> requests = List.of(
                new PaymentRequest("Ana", new BigDecimal("100.00"), PaymentType.PIX, LocalTime.of(14, 0)),
                new PaymentRequest("Bruno", new BigDecimal("100.00"), PaymentType.CARTAO, LocalTime.of(14, 0)),
                new PaymentRequest("Carla", new BigDecimal("100.00"), PaymentType.BOLETO, LocalTime.of(14, 0)),
                new PaymentRequest("Diego", new BigDecimal("15000.00"), PaymentType.PIX, LocalTime.of(10, 0)),
                new PaymentRequest("Elisa", new BigDecimal("2500.00"), PaymentType.CARTAO, LocalTime.of(3, 30)),
                new PaymentRequest("fraudador", new BigDecimal("50.00"), PaymentType.PIX, LocalTime.of(12, 0))
        );

        System.out.println("=== Simulador de Pagamentos ===");
        for (PaymentRequest request : requests) {
            PaymentResult result = facade.pay(request);
            System.out.println("  -> " + result.status() + " | " + result.description());
            System.out.println();
        }

        System.out.println("=== Log de auditoria (Singleton) ===");
        AuditLog.INSTANCE.entries().forEach(e -> System.out.println("  " + e));
    }
}
