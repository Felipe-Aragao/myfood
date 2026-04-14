package br.ufal.ic.myfood.exceptions;

public class MuitosPedidosEmAbertoException extends Exception {
    public MuitosPedidosEmAbertoException() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
}
