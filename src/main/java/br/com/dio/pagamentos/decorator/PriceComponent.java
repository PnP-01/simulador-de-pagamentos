package br.com.dio.pagamentos.decorator;

import java.math.BigDecimal;

/** DECORATOR (componente): qualquer coisa que tenha um valor total e uma descricao. */
public interface PriceComponent {
    BigDecimal total();

    String description();
}
