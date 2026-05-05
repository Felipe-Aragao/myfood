package br.ufal.ic.myfood.exceptions;

public class PedidoNaoEstaSendoPreparadoException extends Exception {

    public PedidoNaoEstaSendoPreparadoException() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }
}
