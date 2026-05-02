package br.ufal.ic.myfood.exceptions;

public class EntregadorDuplicadoException extends Exception {

    public EntregadorDuplicadoException() {
        super("Entregador ja trabalha para essa empresa");
    }
}
