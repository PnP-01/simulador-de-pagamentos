package br.com.dio.pagamentos.decorator;

/** DECORATOR (base): embrulha outro PriceComponent e altera total/descricao. */
public abstract class PriceDecorator implements PriceComponent {
    protected final PriceComponent inner;

    protected PriceDecorator(PriceComponent inner) {
        this.inner = inner;
    }
}
