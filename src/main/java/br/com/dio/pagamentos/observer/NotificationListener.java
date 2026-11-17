package br.com.dio.pagamentos.observer;

import br.com.dio.pagamentos.model.PaymentResult;

import java.io.PrintStream;

/** Simula o envio de notificacao ao cliente (imprime no console). */
public class NotificationListener implements PaymentListener {
    private final PrintStream out;

    public NotificationListener(PrintStream out) {
        this.out = out;
    }

    @Override
    public void onPaymentProcessed(PaymentResult result) {
        out.println("[NOTIFICACAO] " + result.request().payer() + ": " + result.message());
    }
}
