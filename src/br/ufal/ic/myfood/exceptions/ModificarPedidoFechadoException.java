package br.ufal.ic.myfood.exceptions;

public class ModificarPedidoFechadoException extends Exception {

    public ModificarPedidoFechadoException() {
        super("Nao e possivel adcionar produtos a um pedido fechado");
    }
}
