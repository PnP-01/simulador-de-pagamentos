package br.com.dio.pagamentos.observer;

import br.com.dio.pagamentos.model.PaymentResult;

public class AuditLogListener implements PaymentListener {
    @Override
    public void onPaymentProcessed(PaymentResult result) {
        AuditLog.INSTANCE.record(result.status() + " | " + result.request().payer()
                + " | " + result.request().type() + " | R$ " + result.finalAmount());
    }
}
