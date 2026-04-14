package br.ufal.ic.myfood.exceptions;

public class PedidoNaoExisteException extends Exception {

    public PedidoNaoExisteException() {
        super("Nao existe pedido em aberto");
    }
}
