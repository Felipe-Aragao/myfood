package br.ufal.ic.myfood.exceptions;

public class RemoverDePedidoFechadoException extends Exception {
    public RemoverDePedidoFechadoException() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
}
