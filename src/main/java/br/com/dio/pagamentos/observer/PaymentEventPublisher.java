package br.com.dio.pagamentos.observer;

import br.com.dio.pagamentos.model.PaymentResult;

import java.util.ArrayList;
import java.util.List;

/** OBSERVER (subject): mantem a lista de ouvintes e os notifica. */
public class PaymentEventPublisher {
    private final List<PaymentListener> listeners = new ArrayList<>();

    public void subscribe(PaymentListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(PaymentListener listener) {
        listeners.remove(listener);
    }

    public void publish(PaymentResult result) {
        listeners.forEach(l -> l.onPaymentProcessed(result));
    }
}
