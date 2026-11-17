package br.com.dio.pagamentos.observer;

import br.com.dio.pagamentos.model.PaymentResult;

/** OBSERVER (interface): quem quer ser avisado quando um pagamento e processado. */
public interface PaymentListener {
    void onPaymentProcessed(PaymentResult result);
}
