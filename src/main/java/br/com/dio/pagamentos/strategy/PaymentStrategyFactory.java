package br.com.dio.pagamentos.strategy;

import br.com.dio.pagamentos.model.PaymentType;

/** FACTORY METHOD (simples): escolhe a Strategy correta para o tipo de pagamento. */
public final class PaymentStrategyFactory {

    private PaymentStrategyFactory() {
    }

    public static PaymentStrategy create(PaymentType type) {
        return switch (type) {
            case PIX -> new PixStrategy();
            case CARTAO -> new CreditCardStrategy();
            case BOLETO -> new BoletoStrategy();
        };
    }
}
